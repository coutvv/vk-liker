package ru.coutvv.vkliker.test;

import java.io.IOException;

import ru.coutvv.vkliker.Factory;

public class AbstractTest {

	protected static Factory factory;
	static {
		String filename = "app.properties";
		try {
			factory = new Factory(filename);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("не удалось инициализировать фабрику");
		}
	}
}
