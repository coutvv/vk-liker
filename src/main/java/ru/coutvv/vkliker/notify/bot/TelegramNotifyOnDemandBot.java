package ru.coutvv.vkliker.notify.bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.TelegramApiException;

import ru.coutvv.vkliker.notify.Notifier;
import ru.coutvv.vkliker.notify.TelegramBot;

public class TelegramNotifyOnDemandBot extends TelegramBot implements Notifier {

	private final static List<String> messages = new ArrayList<>();
	
	public TelegramNotifyOnDemandBot(String token, String chatId) throws TelegramApiException {
		super(token, chatId, (update, bot) -> {
			for(String msg : messages) {
				bot.sending(msg);
				messages.remove(msg);
			}
		});
	}

	@Override
	public void print(String msg) {
		messages.add(msg);
	}

	
}
