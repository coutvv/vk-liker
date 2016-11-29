package ru.coutvv.vkliker;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Входная точка
 * 
 * @author lomovtsevrs
 */
public class Main {

	/**
	 * время чтобы нас не замели на капчу
	 */
	final static int SLEEP_TIME = 500;
	
	/**
	 * имя файла с токеном и юзерИд
	 */
	final static String FILENAME = "app.properties";
	
	/**
	 * милый лайкер ^_^
	 */
	final LikerControl liker;
	
	public LikerControl getLiker() {return liker;}
	
	public Main() throws IOException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILENAME);
		Properties props = new Properties();
		props.load(in);
		String token = props.getProperty("token");
		String userId = props.getProperty("userId");
		liker = new Liker(Integer.parseInt(userId), token);
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		LikerControl liker = new Main().getLiker();
		List<Post> posts = liker.getLastNews(20);
		for(Post post : posts) {
			liker.likePost(post);
			Thread.sleep(SLEEP_TIME);
		}
	}
	
}