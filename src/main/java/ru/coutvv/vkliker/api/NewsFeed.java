package ru.coutvv.vkliker.api;

import java.util.List;

import ru.coutvv.vkliker.data.Post;

/**
 * Интерфейс получения данных из новостной ленты
 * 
 * @author lomovtsevrs
 */
public interface NewsFeed {

	/**
	 * Получить все посты за последние N часов
	 * @param hour -- время назад
	 * @return
	 */
	List<Post> getLastPosts(int hour);
	
	/**
	 * Получить все последние count постов
	 * @param count -- количество постов
	 * @return
	 */
	List<Post> getLastCountPosts(int count);
	
	/**
	 * Получить все посты, сделаные людьми за последние hour часов
	 * @param hour -- время назад
	 * @return
	 */
	List<Post> getLastPersonPosts(int hour);
	
	/**
	 * Получить все последние count постов, сделанные людьми
	 * @param count -- количество постов
	 * @return
	 */
	List<Post> getLastCountPersonPosts(int count);
	
}
