package ru.coutvv.vkliker.test;

import java.util.List;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.coutvv.vkliker.api.monitor.CommentMonitor;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.api.repository.ComplexFeedData;

public class TestLikeWatcher extends AbstractTest {
	CommentMonitor monitor;
	
	public static void main(String[] args) throws ClientException, ApiException {
		CommentMonitor monitor = factory.createCommentMonitor();
		List<Item> posts =
				ComplexFeedData.parseItems(factory.createPostRepository().getLast(1));
		System.out.println(posts);
		monitor.addToWatch(posts.get(0));
	}
}
