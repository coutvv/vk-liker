package ru.coutvv.vkliker;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import ru.coutvv.vkliker.api.NewsManager;
import ru.coutvv.vkliker.gui.desk.VkLikerGui;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.notify.TelegramBot;

import java.io.IOException;
import java.util.Arrays;

/**
 * Входная точка
 * 
 * @author lomovtsevrs
 */
public class EntryPoint {
	
	final static String FILENAME = "app.properties";
	
	static NewsManager newsManager;
	static Factory fac;

	public static void main(String[] args) {

		if(Arrays.asList(args).contains("console")) {
			consoleMode(args);
		} else {
			VkLikerGui.main(args);//GUI by default
		}

	}

	/**
	 * Консоль-мод лайкера
	 * @param args
	 */
	private static void consoleMode(String[] args) {
		try {
			fac = new Factory(FILENAME);
			newsManager = fac.createNewsManager();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initLogSystem();

		if(args.length >= 1 && Arrays.asList(args).contains("loop")) {
			newsManager.scheduleLike(15);

			if(Arrays.asList(args).contains("comment")) { //отслеживаем комменты
				newsManager.commentWatching(15, 420); //4 часа отслеживаем, каждые 15 минут лайкаем новые
			}
		} else {
			newsManager.likeLastPosts(24);
			System.exit(0);//вырубаемси
		}
	}
	
	private static void initLogSystem() {
		TelegramBot bot = fac.createStaticalNotifier();

		TelegramBotsApi api = new TelegramBotsApi();
		try {
			api.registerBot(bot);
		} catch (TelegramApiException e) {
			throw new IllegalArgumentException("telegram register bot failed");
		}
		Logger.init(bot);
	}
}
