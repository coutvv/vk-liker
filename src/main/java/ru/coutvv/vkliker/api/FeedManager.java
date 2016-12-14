package ru.coutvv.vkliker.api;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import ru.coutvv.vkliker.data.Post;

public class FeedManager {
	private final UserActor actor;
	private final VkApiClient vk;
	
	private final NewsFeed feed;
	private final PostLiker liker;
	
	public FeedManager(int userId, String token) {
		actor = new UserActor(userId, token);
		TransportClient tc = HttpTransportClient.getInstance();
		vk = new VkApiClient(tc, new Gson());
		
		feed = new NewsRepository(actor, vk);
		liker = new PostLiker(actor, vk);
	}
	
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
	
	public void scheduleLike(int minutes) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					System.out.println("[ lets like my feed forever ]");
					for(;;) {
						likeAllLastHours(1);
						System.out.println("[ waiting next session ] this ended at " + new Date());

						Thread.sleep(minutes*60*1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
