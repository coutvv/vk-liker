package ru.coutvv.vkliker;

import java.io.IOException;
import java.util.Arrays;

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
		
		if(args.length >= 1 && Arrays.asList(args).contains("loop")) {
			fm.scheduleLike(15);

			if(Arrays.asList(args).contains("comment")) { //отслеживаем комменты
				fm.commentWatching(15, 420); //4 часа отслеживаем, каждые 15 минут лайкаем новые
			}
		} else {
			fm.likeAllLastHours(2);
		}
		

	}
	
}
