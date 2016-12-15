package ru.coutvv.vkliker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ru.coutvv.vkliker.api.FeedManager;

public class EntryPoint {
	
	final static String FILENAME = "app.properties";
	
	static FeedManager fm;

	public static void main(String[] args) throws IOException, InterruptedException {
		
		initFeedManager();
		
		if(args.length == 1 && args[0].equals("loop")) {
			fm.scheduleLike(15);
		} else {
			fm.likeAllLastHours(2);
		}
		
	}
	
	static void initFeedManager() throws IOException {

		InputStream in = EntryPoint.class.getClassLoader().getResourceAsStream(FILENAME);
		Properties props = new Properties();
		props.load(in);
		in.close();
		String token = props.getProperty("token");
		int id = Integer.parseInt(props.getProperty("userId"));
		
		fm = new FeedManager(id, token);
	}
	
	
	
}
