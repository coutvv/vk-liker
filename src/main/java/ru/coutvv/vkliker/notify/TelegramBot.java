package ru.coutvv.vkliker.notify;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class TelegramBot extends TelegramLongPollingBot implements Notifier  {
	
	private String token;
	private final String NAME = "vkliker";
	private final String chatId;
	private final ResponseStrategy resp;
	
	public TelegramBot(String token, String chatId, ResponseStrategy response) throws TelegramApiException {
		this.token = token;
		this.chatId = chatId;
		this.resp = response;
	}
	
	@Override
	public String getBotUsername() {
		return NAME;
	}

	@Override
	public void onUpdateReceived(Update update) {
		resp.onUpdate(update, this);
	}

	@Override
	public String getBotToken() {
		return token;
	}
	

	public void sending(String text) {
		SendMessage message = new SendMessage();
		message.setText(text);
		message.setChatId(chatId);
		try {
			sendMessage(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

}
