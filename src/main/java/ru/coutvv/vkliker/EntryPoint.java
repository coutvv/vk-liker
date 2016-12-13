package ru.coutvv.vkliker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import ru.coutvv.vkliker.api.NewsFeed;
import ru.coutvv.vkliker.api.NewsRepository;
import ru.coutvv.vkliker.api.PostLiker;
import ru.coutvv.vkliker.data.Post;

public class EntryPoint {
	
	final static String FILENAME = "app.properties";
	
	static NewsFeed feed;
	static PostLiker liker;

	public static void main(String[] args) throws IOException, InterruptedException {
		if(args.length == 1 && args[0].equals("loop")) {
			System.out.println("[ lets like my feed forever ]");
			for(;;) {
				final long timeToSleep = 60*60*1000;
				likeInHour();
				
				System.out.println("[ waiting next session ] this ended at " + new Date());
				Thread.sleep(timeToSleep);
			}
		} else {
			likeInHour();
		}
		
	}
	
	/**
	 * Ставим лайки всем постам за последний час
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private static void likeInHour() throws InterruptedException, IOException {
		InputStream in = EntryPoint.class.getClassLoader().getResourceAsStream(FILENAME);
		Properties props = new Properties();
		props.load(in);
		String token = props.getProperty("token");
		String userId = props.getProperty("userId");
		
		int id = Integer.parseInt(userId);
		
		feed = new NewsRepository(id, token);
		liker = new PostLiker(id, token);
		List<Post> posts = feed.getLastPosts(5);
		for(Post post : posts) {
			liker.like(post);
			Thread.sleep(500);
		}
	}
	
}
