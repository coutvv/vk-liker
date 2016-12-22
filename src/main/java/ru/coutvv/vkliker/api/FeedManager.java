package ru.coutvv.vkliker.api;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Post;
import ru.coutvv.vkliker.data.repository.CommentRepository;
import ru.coutvv.vkliker.data.repository.PostRepository;

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
		List<Post> posts = feed.getLastPosts(hours);
		Random rand = new Random();
		for(Post post: posts) {
			long delta = Math.round(rand.nextDouble()*500),
				 timeSleep = 500 + (rand.nextBoolean() ? delta : -delta);
			
			if(!post.isLiked()) {
				liker.like(post);
				
				try {
					Thread.sleep(timeSleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
				
		}
	}
	
	/**
	 * Лайкаем все посты с комментариями
	 * @param hours
	 */
	public void likeAllLastHoursWithComments(int hours) {
		List<Post> posts = feed.getLastPosts(hours);
		likeAllLastHours(hours);
		for(Post post : posts) {
			List<Comment> comments = coms.getComments(post);
			post.setComments(comments);
			liker.likeAllComments(post, 500);
			try {
				Thread.sleep(new Random().nextInt(500) + 500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Периодически лайкать всю ленту
	 * 
	 * @param minutes -- период в минутах
	 */
	public void scheduleLike(int minutes) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					System.out.println("[ lets like my feed forever ]");
					for(;;) {
						int hours = minutes/60 + 1;//период за который получим новости
						likeAllLastHours(hours);
						System.out.println("[ waiting next session ] this ended at " + new Date());

						Thread.sleep(minutes*60*1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
}
