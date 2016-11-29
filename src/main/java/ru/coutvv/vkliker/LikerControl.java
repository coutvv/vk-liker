package ru.coutvv.vkliker;

import java.util.List;

public interface LikerControl {

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
