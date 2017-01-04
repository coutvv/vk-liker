package ru.coutvv.vkliker.test.notify;

import java.io.IOException;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.notify.TelegramNotifierBot;

public class TestTelegram {
	
	private static String FILENAME = "app.properties";

	public static void main(String[] args) throws TelegramApiException, IOException {
		Factory fac = new Factory(FILENAME);
		
		TelegramNotifierBot bot = fac.createNotifier();
		
		TelegramBotsApi api = new TelegramBotsApi();
		api.registerBot(bot);
		bot.print("my Little Pony");
	}
}
