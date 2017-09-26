package ru.coutvv.vkliker;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.telegram.telegrambots.TelegramApiException;
import ru.coutvv.vkliker.api.*;
import ru.coutvv.vkliker.api.monitor.CommentMonitor;
import ru.coutvv.vkliker.api.repository.*;
import ru.coutvv.vkliker.notify.TelegramBot;
import ru.coutvv.vkliker.notify.bot.TelegramNotifierBot;
import ru.coutvv.vkliker.notify.bot.TelegramNotifyOnDemandBot;
import ru.coutvv.vkliker.notify.bot.TelegramStatisticalBot;
import ru.coutvv.vkliker.util.Consts;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Фабрика для создавания всяких интересеных штук
 * 
 * @author lomovtsevrs
 */
public class Factory {

    private static final Log log = LogFactory.getLog(Factory.class);
	
	private String token;
	private int userId;
	
	private UserActor actor;
	private VkApiClient vk;
	
	/** data для нотификатора в Телеграм */
	private String teleToken;
	private String chatId;

	public Factory() throws IOException {
		this(Consts.APP_PROPERTIES_FILENAME);
	}

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
		    log.warn("Error in construct", e);
		}
	}

	public NewsManager createNewsManager() {
		return new NewsManagerImpl(actor, vk);
	}

	public WallManager createWallManager() {
		return new WallManagerImpl(actor, vk, createProfileRepository());
	}

	public ProfileRepository createProfileRepository() {
		return new ProfileRepositoryImpl(actor, vk);
	}
	
	public CommentRepository createCommentRepository() {
		return new CommentRepositoryImpl(actor, vk);
	}
	
	public PostRepository createPostRepository() {
		return new PostRepositoryImpl(actor, vk);
	}
	
	public Liker createPostLiker() {
		return new Liker(actor, vk);
	}
	
	public CommentMonitor createCommentMonitor(){
		return new CommentMonitor(createPostLiker(), createCommentRepository()); 
	}
	
	public TelegramBot createNotifier() {
		try {
			TelegramNotifierBot teleBot = new TelegramNotifierBot(teleToken, chatId);
			return teleBot;
		} catch (TelegramApiException e) {
		    log.debug("CreateNotifier", e);
            throw new IllegalArgumentException("can't create notifier", e);
        }
	}

	public TelegramBot createNotCozyNotifier() {
		try {
			TelegramNotifyOnDemandBot teleBot = new TelegramNotifyOnDemandBot(teleToken, chatId);
			return teleBot;
		} catch (TelegramApiException e) {
            log.debug("createNotCozyNotifier", e);
            throw new IllegalArgumentException("can't create notifier", e);
        }
	}

	public TelegramBot createStaticalNotifier() {
		try {
			TelegramStatisticalBot teleBot = new TelegramStatisticalBot(teleToken, chatId);
			return teleBot;
		} catch (TelegramApiException e) {
            log.debug("createStaticalNotifier", e);
            throw new IllegalArgumentException("can't create notifier", e);
        }
	}
	
}
