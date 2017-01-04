package ru.coutvv.vkliker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.telegram.telegrambots.TelegramApiException;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import ru.coutvv.vkliker.api.FeedManager;
import ru.coutvv.vkliker.api.Liker;
import ru.coutvv.vkliker.api.monitor.CommentMonitor;
import ru.coutvv.vkliker.data.repository.CommentRepository;
import ru.coutvv.vkliker.data.repository.PostRepository;
import ru.coutvv.vkliker.notify.TelegramNotifierBot;

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
	
	/** data для нотификатора в Телеграм**/
	private String teleToken;
	private String chatId;

	public Factory(String propertiesFilename) throws IOException {
		InputStream in = Factory.class.getClassLoader().getResourceAsStream(propertiesFilename); 
		Properties props = new Properties();
		props.load(in);
		
		token = props.getProperty("token");
		userId = Integer.parseInt(props.getProperty("userId"));
		
		//telegram info
		teleToken = props.getProperty("teleToken");
		chatId = props.getProperty("chatId");
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
	
	public PostRepository createPostRepository() {
		return new PostRepository(actor, vk);
	}
	
	public Liker createPostLiker() {
		return new Liker(actor, vk);
	}
	
	public CommentMonitor createCommentMonitor(){
		return new CommentMonitor(createPostLiker(), createCommentRepository()); 
	}
	
	public TelegramNotifierBot createNotifier() {
		try {
			TelegramNotifierBot teleBot = new TelegramNotifierBot(teleToken, chatId);
			return teleBot;
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException("can't create notifier");
	}
	
}
