package ru.coutvv.vkliker;

import java.io.IOException;
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
		
		if(args.length == 1 && args[0].equals("loop")) {
			fm.scheduleLike(15);
		} else if(args.length == 1 && args[0].equals("comments")) {
			CommentRepository comRep = fac.createCommentRepository();
			Liker lkr = fac.createPostLiker();
			for(Post post : fac.createPostRepository().getLastPosts(2)) {
				LagUtil.lag();
				try {
					List<Comment> coms = comRep.getComments(post);
					for(Comment com : coms) {
						lkr.like(com, post.getOwnerId());
						LagUtil.lag();
					}
				} catch (Exception e) {
					System.out.println("не получилось для поста: "+ post.getOwnerName());
				}
			}
			
		} else {
			fm.likeAllLastHours(2);
		}
	}
	
}
