package ru.coutvv.vkliker.api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;

import ru.coutvv.vkliker.api.monitor.LikeCommentListener;
import ru.coutvv.vkliker.api.monitor.CommentMonitor;
import ru.coutvv.vkliker.api.entity.Group;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.api.entity.Profile;
import ru.coutvv.vkliker.api.repository.CommentRepository;
import ru.coutvv.vkliker.api.repository.PostRepository;
import ru.coutvv.vkliker.api.entity.ComplexFeedData;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.util.LagUtil;

/**
 * Менеджер для управления новостной лентой
 * 
 * @author lomovtsevrs
 */
public class FeedManager {
	private final UserActor actor;
	private final VkApiClient vk;

	private final PostRepository feed;
	private final Liker liker;
	private final CommentRepository coms;

	public FeedManager(UserActor actor, VkApiClient vkClient) {
		this.actor = actor;
		this.vk = vkClient;
		feed = new PostRepository(actor, vk);
		liker = new Liker(actor, vk);
		coms = new CommentRepository(actor, vk);

	}

	private Map<Long, String> names;

	private void createNames(Map<Long, Profile> profiles, Map<Long, Group> groups) {
		names = new HashMap<>();
		for(Map.Entry<Long, Profile> e : profiles.entrySet()) {
			names.put(e.getKey(), e.getValue().getFirstName() + " " + e.getValue().getLastName());
		}

		for(Map.Entry<Long, Group> e : groups.entrySet()) {
			names.put(e.getKey(), e.getValue().getName());
		}

	}
	/**
	 * Залайкать все новости в ленте за последние hours часов
	 * 
	 * @param hours
	 */
	public void likeAllLastHours(int hours) {
		ComplexFeedData cfd = feed.getFeedLastMinutes(hours * 60);
		createNames(cfd.getProfiles(), cfd.getGroups());
		for(Item item : cfd.getItems()) {
			if(item.getLikes().getCanLike() == 1 && item.getSourceId() != (long)actor.getId()) {//тип не лайкать свои посты
				liker.likeAndSave(item, names.get(item.getSourceId()));
				long ownerId = Math.abs(item.getSourceId());//тип у груп, чтоб тоже норм было
				if(item.getSourceId() > 0) {
					Logger.log("liked post by person: " + cfd.getProfiles().get(ownerId));
				} else {
					Logger.log("liked post by community: " + cfd.getGroups().get(ownerId));
				}
				
				LagUtil.lag();
			}
		}
	}


	/**
	 * Периодически лайкать всю ленту
	 * 
	 * @param minutes
	 *            -- период в минутах
	 */
	public ExecutorService scheduleLike(int minutes) {

		ExecutorService exec = Executors.newSingleThreadExecutor();
		Runnable task = () -> {
			Logger.log("[ lets like my feed forever ]");
			for (;;) {
				int hours = minutes / 60 + 1;// период за который получим
				// новости
				try {
					likeAllLastHours(hours);
				} catch (Exception e) {
					Logger.log("[ Can't reach some feauture ]" + e.getMessage());
				}
				Logger.log("[ waiting next session ] this ended at " + new Date());

				LagUtil.lag(minutes * 60 * 1000);
			}
		};
		exec.execute(task);

		return exec;
	}

	/**
	 * Следить за комментариями постов
	 */
	public void commentWatching(int minutes, long timeout) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				CommentMonitor cm = new CommentMonitor(liker, coms, timeout, minutes);
				LikeCommentListener cll = new LikeCommentListener(liker);
				cm.addListener(cll);
				System.out.println("<<Watching for comments!>>");
				for (;;) {
					System.out.println("[ Comment session ] this ended at " + new Date());
					List<Item> posts = feed.getLastPostsInMin(minutes);
					for(Item post : posts) {
						cm.addToWatch(post);
					}
					LagUtil.lag(minutes * 60 * 1000);
				}
			}
		}).start();
	}

}
