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
		Factory fac = null;
		try {
			fac = new Factory(FILENAME);
			fm = fac.createFeedManager();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(args.length == 1 && args[0].equals("loop")) {
			fm.scheduleLike(15);
		} else {
			fm.likeAllLastHours(2);
		}
		
	}
	
}
