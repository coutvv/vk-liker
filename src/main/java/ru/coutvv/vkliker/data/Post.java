package ru.coutvv.vkliker.data;

/**
 * Данные для поста, который лайкнем
 * 
 * @author lomovtsevrs
 */
public class Post {
	
	long postId;
	long sourceId;
	String ownerName;

	public Post(String ownerName, long source, long post) {
		this.ownerName = ownerName;
		sourceId = source;
		postId = post;
	}

	public long getSourceId() {
		return sourceId;
	}

	public long getPostId() {
		return postId;
	}

	public String getOwnerName() {
		return ownerName;
	}
	
	@Override
	public String toString() {
		return "user: " + ownerName + "\t|\tid: " + postId;
	}
}
