package ru.coutvv.vkliker.data;

/**
 * Данные для поста, который лайкнем
 * 
 * @author lomovtsevrs
 */
public class Post {
	String type;
	int sourceId;
	int postId;
	
	public Post(String type, int source, int post) {
		this.type = type;
		sourceId = source;
		postId = post;
	}
	
	public String getType() {return type;}
	public int getSourceId() {return sourceId;}
	public int getPostId() {return postId;}
}
