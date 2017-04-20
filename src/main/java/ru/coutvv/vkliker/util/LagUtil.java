package ru.coutvv.vkliker.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;

public class LagUtil {

    private static final Log log = LogFactory.getLog(LagUtil.class);
	private static final long TIMEOUT = 500;
	private static final Random rand = new Random();
	/**
	 * Задержка 500+-250 sec
	 */
	public static void lag() {
		try { //waiting
			long dtime = rand.nextInt(250);
			dtime = (rand.nextBoolean() ? dtime : -dtime);//warious time
			Thread.sleep(TIMEOUT + dtime);
		} catch (InterruptedException e) {
			log.error("sleep interrupt", e);
		}
	}
	
	public static void lag(long timeout) {
		try { //waiting
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
            log.error("sleep interrupt", e);
		}
	}
}
