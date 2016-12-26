package ru.coutvv.vkliker.test;

import java.util.List;

import ru.coutvv.vkliker.api.monitor.CommentMonitor;
import ru.coutvv.vkliker.data.entity.Post;

public class TestLikeWatcher extends AbstractTest {
	CommentMonitor monitor;
	
	public static void main(String[] args) {
		CommentMonitor monitor = factory.createCommentMonitor();
		List<Post> posts = factory.createPostRepository().getLastCountPosts(1);
		System.out.println(posts);
		monitor.addToWatch(posts.get(0));
	}
}
