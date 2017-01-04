package ru.coutvv.vkliker.notify;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class TelegramNotifierBot extends TelegramLongPollingBot implements Notifier {

	private String token;
	
	private final String NAME = "vkliker";
	private final String chatId;
	
	public TelegramNotifierBot(String token, String chatId) throws TelegramApiException {
		this.token = token;
		this.chatId = chatId;
	}
	
	@Override
	public String getBotUsername() {
		return NAME;
	}

	@Override
	public void onUpdateReceived(Update update) {
		String id = update.getMessage().getChatId().toString();
		String answer = "This chat ID:  " + id;
		sending(id, answer);
	}

	@Override
	public void print(String msg) {
		sending(chatId, msg);
	}
	
	public void sending(String chatId, String text) {
		SendMessage message = new SendMessage();
		message.setText(text);
		message.setChatId(chatId);
		try {
			sendMessage(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getBotToken() {
		return token;
	}

}
