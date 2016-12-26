package ru.coutvv.vkliker;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import ru.coutvv.vkliker.api.FeedManager;
import ru.coutvv.vkliker.api.Liker;
import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Post;
import ru.coutvv.vkliker.data.repository.CommentRepository;
import ru.coutvv.vkliker.util.LagUtil;

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
				fm.commentWatching(15, 420);
			}
		} else {
			fm.likeAllLastHours(2);
		}
		

	}
	
}
