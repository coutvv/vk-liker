package ru.coutvv.vkliker.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Post;
import ru.coutvv.vkliker.data.repository.CommentRepository;
import ru.coutvv.vkliker.util.LagUtil;

public class CommentMonitor {

	private final Liker liker;
	private final CommentRepository commentRep;
	
	private Map<Post, Integer> watchPosts;
	
	private final long WATCH_TIME = 20*60;
	private LikeWatcher watcher;
	
	public CommentMonitor(Liker liker, CommentRepository commentRep) {
		this.liker = liker;
		this.commentRep = commentRep;
		watchPosts = new HashMap<>();
		watcher = new LikeWatcher();
		new Thread(watcher).start();
	}
	
	public void addToWatch(Post post) {
		List<Comment> cms = commentRep.getComments(post);
		watchPosts.put(post, cms.size());
	}
	
	public void stop() {
		watcher.kill();
	}
	
	private class LikeWatcher implements Runnable  {

		@Override
		public void run() {

			for(;;) {
				System.out.println("!!!!пробуем полайкать!!! " );
				for(Post post : watchPosts.keySet()){

					long endDate = post.getDate() + WATCH_TIME;
					if(endDate > System.currentTimeMillis()/1000) {
						List<Comment> cms = commentRep.getComments(post);
						if(!cms.isEmpty()) {
							for(Comment cm : cms) {
								liker.like(cm, post.getOwnerId());
								System.out.println("Комментарий: \"" + cm.getText() + "\" был лайкнут");
								LagUtil.lag();
							}
						}

						System.out.println("end date: " + endDate);
						System.out.println("current  : " +System.currentTimeMillis()/1000);
					} else {
						watchPosts.remove(post);
						System.out.println("Next post was removed: \n" + post);
						System.out.println("end date: " + endDate);
						System.out.println("current  : " +System.currentTimeMillis()/1000);
					}
				}
				LagUtil.lag(10*1000);
				if(die) break;
			}
			
		}
		private boolean die = false;
		public void kill() {
			die = true;
		}
		
	}
}

