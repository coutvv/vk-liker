package ru.coutvv.vkliker.notify;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public abstract class TelegramBot extends TelegramLongPollingBot implements Notifier  {

    private final Log log = LogFactory.getLog(this.getClass());

	private String token;
	private final static String NAME = "vkliker";
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
		    log.warn("Send message error", e);
		}
	}

	public void sending(String text, ReplyKeyboard keyboard) {
		SendMessage message = new SendMessage();
		message.setText(text);
		message.setChatId(chatId);
		message.setReplyMarkup(keyboard);
		try {
			sendMessage(message);
		} catch (TelegramApiException e) {
		    log.warn("Send message error", e);
		}
	}

}
