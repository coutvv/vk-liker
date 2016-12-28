package ru.coutvv.vkliker.api;

import java.util.Date;
import java.util.List;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;

import ru.coutvv.vkliker.api.monitor.CommentLikerListener;
import ru.coutvv.vkliker.api.monitor.CommentMonitor;
import ru.coutvv.vkliker.data.entity.Item;
import ru.coutvv.vkliker.data.repository.CommentRepository;
import ru.coutvv.vkliker.data.repository.PostRepository;
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

	/**
	 * Залайкать все новости в ленте за последние hours часов
	 * 
	 * @param hours
	 */
	public void likeAllLastHours(int hours) {
		List<Item> posts = feed.getLastPosts(hours);
		for (Item post : posts) {
			if (post.getLikes().getCanLike() == 1) {
				liker.like(post);
				LagUtil.lag();
			}

		}
	}

	/**
	 * Лайкаем все посты с комментариями
	 * 
	 * @param hours
	 */
//	public void likeAllLastHoursWithComments(int hours) {
//		List<Item> posts = feed.getLastPosts(hours);
//		likeAllLastHours(hours);
//		for (Item post : posts) {
//			List<Comment> comments = coms.getComments(post);
//			post.setComments(comments);
//			liker.likeAllComments(post, 500);
//			LagUtil.lag();
//		}
//	}

	/**
	 * Периодически лайкать всю ленту
	 * 
	 * @param minutes
	 *            -- период в минутах
	 */
	public void scheduleLike(int minutes) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("[ lets like my feed forever ]");
				for (;;) {
					int hours = minutes / 60 + 1;// период за который получим
													// новости
					likeAllLastHours(hours);
					System.out.println("[ waiting next session ] this ended at " + new Date());

					LagUtil.lag(minutes * 60 * 1000);
				}
			}
		}).start();
	}

	/**
	 * Следить за комментариями постов
	 */
	public void commentWatching(int minutes, long timeout) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				CommentMonitor cm = new CommentMonitor(liker, coms, timeout, minutes);
				CommentLikerListener cll = new CommentLikerListener(liker);
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
