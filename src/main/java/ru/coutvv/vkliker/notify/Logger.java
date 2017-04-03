package ru.coutvv.vkliker.notify;

import java.util.ArrayList;
import java.util.List;

public class Logger {

	private static volatile List<Notifier> notifiers;

	/**
	 * Инициализация нотифайера(добавление в список)
	 * @param notifier
	 */
	public static void init(Notifier notifier) {

		if(notifiers == null)
			synchronized (Logger.class) {
				notifiers = new ArrayList<>();
			}

		notifiers.add(notifier);
	}

	
	public static void log(String msg) {
		if(notifiers == null)
			throw new IllegalArgumentException("Notifier is not initialized");

		for(Notifier notifier : notifiers)
			notifier.print(msg);
	}
}
