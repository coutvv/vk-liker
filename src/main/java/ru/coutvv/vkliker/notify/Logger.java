package ru.coutvv.vkliker.notify;

public class Logger {

	public static void init(Notifier notifier) {
		Logger.notifier = notifier;
	}
	
	private static Notifier notifier; 
	
	public static void log(String msg) {
		if(notifier == null) 
			throw new IllegalArgumentException("Notifier is not initialized");
		notifier.print(msg);
	}
}
