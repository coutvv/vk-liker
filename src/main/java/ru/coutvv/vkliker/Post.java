package ru.coutvv.vkliker;

/**
 * Данные для поста, который лайкнем
 * 
 * @author lomovtsevrs
 */
class Post {
	String type;
	int sourceId;
	int postId;
	
	Post(String type, int source, int post) {
		this.type = type;
		sourceId = source;
		postId = post;
	}
	
	String getType() {return type;}
	int getSourceId() {return sourceId;}
	int getPostId() {return postId;}
}
