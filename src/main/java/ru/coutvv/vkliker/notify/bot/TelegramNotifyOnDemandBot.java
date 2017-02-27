package ru.coutvv.vkliker.notify.bot;

import java.util.*;

import org.telegram.telegrambots.TelegramApiException;

import ru.coutvv.vkliker.notify.Notifier;
import ru.coutvv.vkliker.notify.TelegramBot;

public class TelegramNotifyOnDemandBot extends TelegramBot {

	private static int count = 0;
	private final int MAX_LIST_MSG_SIZE = 10;
	private final static List<String> messages = new LinkedList<>();

	public TelegramNotifyOnDemandBot(String token, String chatId) throws TelegramApiException {
		super(token, chatId, (update, bot) -> {
			bot.sending("I like for you " + count + " posts. \n And these last " +
						messages.size() + " posts:");

			for(String msg : messages) {
				bot.sending(msg);
			}
		});
	}

	@Override
	public void print(String msg) {
		if(messages.size() >= MAX_LIST_MSG_SIZE)
			messages.remove(0);
		messages.add(msg);
		count++;
	}

	
}
