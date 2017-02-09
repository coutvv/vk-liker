package ru.coutvv.vkliker.notify;

import org.telegram.telegrambots.api.objects.Update;

/**
 * Что будем делать, если получили сообщеньку от пользователя в телеграме
 * 
 * @author lomovtsevrs
 */
public interface ResponseStrategy {
	void onUpdate(Update update, TelegramBot bot);
}
