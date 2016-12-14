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
	boolean isLiked;


	public Post(String ownerName, long source, long post, boolean isLiked) {
		this.ownerName = ownerName;
		sourceId = source;
		postId = post;
		this.isLiked = isLiked;
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

	public boolean isLiked() {
		return isLiked;
	}
	
	@Override
	public String toString() {
		return "user: " + ownerName + "\t|\tid: " + postId;
	}
}
