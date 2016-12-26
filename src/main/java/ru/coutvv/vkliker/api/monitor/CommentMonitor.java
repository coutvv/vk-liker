package ru.coutvv.vkliker.api.monitor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.coutvv.vkliker.api.Liker;
import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Post;
import ru.coutvv.vkliker.data.repository.CommentRepository;
import ru.coutvv.vkliker.util.LagUtil;

/**
 * Наблюдатель за комментариями к постам(применим паттерн наблюдатель)
 * 
 * @author lomovtsevrs
 */
public class CommentMonitor extends Observer {

	private final Liker liker;
	
	private final CommentRepository commentRep;
	
	/**
	 * Число -- количество комментариев, которые мы пролайкали
	 */
	private Map<Post, Integer> watchPosts;
	
	private CommentWatcher watcher;
	
	/**
	 * Период проверки обновлений в секундах
	 */
	private long period = 10 * 60; //10 минут по умолчанию
	/**
	 * Общее время наблюдения с момента создания поста
	 */
	private long timeout = 1 * 60 * 60; //1 час по умолчанию
	
	public CommentMonitor(Liker liker, CommentRepository commentRep) {
		this.liker = liker;
		this.commentRep = commentRep;
		watchPosts = new HashMap<>();
		watcher = new CommentWatcher();
		new Thread(watcher).start();
	}
	
	/**
	 * 
	 * @param liker
	 * @param commentRep
	 * @param timeout в минутах 
	 * @param period в минутах
	 */
	public CommentMonitor(Liker liker, CommentRepository commentRep, long timeout, long period) {
		this(liker, commentRep);
		this.timeout = timeout * 60;
		this.period = period * 60;
	}
	
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	
	public void setPeriod(long period){ 
		this.period = period;
	}
	
	public void addToWatch(Post post) {
		List<Comment> cms = commentRep.getComments(post);
		watchPosts.put(post, cms.size());
	}
	
	public void stop() {
		watcher.kill();
	}
	
	
	
	private class CommentWatcher implements Runnable  {
		private final long WATCH_TIME = 20*60; //в секундах
		@Override
		public void run() {

			for(;;) {
				for(Post post : watchPosts.keySet()){

					long endDate = post.getDate() + WATCH_TIME;
					if(endDate > System.currentTimeMillis()/1000) {
						List<Comment> cms = commentRep.getComments(post, watchPosts.get(post));
						if(!cms.isEmpty()) {
							System.out.println("Появились новые комментарии. Пост ∈ " + post.getOwnerName());
							digest(post, cms);
						}
					} else {
						watchPosts.remove(post);
					}
				}
				LagUtil.lag(period*1000);
				if(die) break;
			}
			
		}
		private boolean die = false;
		public void kill() {
			die = true;
		}
		
	}

}

