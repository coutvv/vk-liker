package ru.coutvv.vkliker.notify.bot;

import java.util.*;

import org.telegram.telegrambots.TelegramApiException;

import ru.coutvv.vkliker.notify.Notifier;
import ru.coutvv.vkliker.notify.TelegramBot;

public class TelegramNotifyOnDemandBot extends TelegramBot {

	private final static List<String> messages = new LinkedList<>();

	public TelegramNotifyOnDemandBot(String token, String chatId) throws TelegramApiException {
		super(token, chatId, (update, bot) -> {
			if(messages.size() == 0) bot.sending("I am alive but msgs is empty now");
			else bot.sending("I have some more msgs: " + messages.size());
			for(String msg : messages) {
				bot.sending(msg);
			}
			for(int i = 0; i < messages.size(); i++)
				messages.remove(i);
		});
	}

	@Override
	public void print(String msg) {
		messages.add(msg);
	}

	
}
