package ru.coutvv.vkliker.notify.bot;

import org.telegram.telegrambots.TelegramApiException;

import ru.coutvv.vkliker.notify.Notifier;
import ru.coutvv.vkliker.notify.TelegramBot;

public class TelegramNotifierBot extends TelegramBot implements Notifier {

	public TelegramNotifierBot(String token, String chatId) throws TelegramApiException {
		super(token, chatId, (update, bot) -> {
			String id = update.getMessage().getChatId().toString();
			String answer = "This chat ID:  " + id;
			bot.sending(answer);
		});
	}

	@Override
	public void print(String msg) {
		this.sending(msg);
	}

}
