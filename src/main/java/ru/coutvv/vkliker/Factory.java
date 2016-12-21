package ru.coutvv.vkliker;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import ru.coutvv.vkliker.api.CommentRepository;
import ru.coutvv.vkliker.api.FeedManager;
import ru.coutvv.vkliker.api.PostLiker;
import ru.coutvv.vkliker.data.Comment;

/**
 * Фабрика для создавания всяких интересеных штук
 * 
 * @author lomovtsevrs
 */
public class Factory {
	
	private String token;
	private int userId;
	
	private UserActor actor;
	private VkApiClient vk;

	public Factory(String propertiesFilename) throws IOException {
		InputStream in = Factory.class.getClassLoader().getResourceAsStream(propertiesFilename); 
		Properties props = new Properties();
		props.load(in);
		
		token = props.getProperty("token");
		userId = Integer.parseInt(props.getProperty("userId"));
		
		actor = new UserActor(userId, token);
		TransportClient tc = HttpTransportClient.getInstance();
		vk = new VkApiClient(tc, new Gson());
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public FeedManager createFeedManager() {
		return new FeedManager(actor, vk);
	}
	
	public CommentRepository createCommentRepository() {
		return new CommentRepository(actor, vk);
	}
	
	public PostLiker createPostLiker() {
		return new PostLiker(actor, vk);
	}
	
	
}