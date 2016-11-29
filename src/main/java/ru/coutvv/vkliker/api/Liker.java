package ru.coutvv.vkliker.api;

import java.util.List;

import ru.coutvv.vkliker.data.Post;

/**
 * Интерфейс лайкания постов в ВК
 * 
 * @author lomovtsevrs
 */
public interface Liker {

	/**
	 * получить последние посты в ленте
	 * @param count -- количество постов
	 * @return
	 */
	public List<Post> getLastNews(int count);
	
	/**
	 * Лайкнуть пост
	 * @param post
	 */
	public void likePost(Post post);
}
