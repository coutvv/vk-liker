package ru.coutvv.vkliker.test;

import java.util.List;

import ru.coutvv.vkliker.api.monitor.CommentMonitor;
import ru.coutvv.vkliker.data.entity.Item;

public class TestLikeWatcher extends AbstractTest {
	CommentMonitor monitor;
	
	public static void main(String[] args) {
		CommentMonitor monitor = factory.createCommentMonitor();
		List<Item> posts = factory.createPostRepository().getLastCountPosts(1);
		System.out.println(posts);
		monitor.addToWatch(posts.get(0));
	}
}
