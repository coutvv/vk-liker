package ru.coutvv.vkliker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ru.coutvv.vkliker.api.FeedManager;

/**
 * Входная точка
 * 
 * @author lomovtsevrs
 */
public class EntryPoint {
	
	final static String FILENAME = "app.properties";
	
	static FeedManager fm;

	public static void main(String[] args) {
		
		initFeedManager();
		
		if(args.length == 1 && args[0].equals("loop")) {
			fm.scheduleLike(15);
		} else {
			fm.likeAllLastHours(2);
		}
		
	}
	
	static void initFeedManager() {
		InputStream in = EntryPoint.class.getClassLoader().getResourceAsStream(FILENAME); 
		try {
			Properties props = new Properties();
			props.load(in);
			
			String token = props.getProperty("token");
			int id = Integer.parseInt(props.getProperty("userId"));
			fm = new FeedManager(id, token);
		} catch (IOException e) {
			throw new IllegalArgumentException("Не удалось получить данные из файла app.properties");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
