package ru.coutvv.vkliker.data.entity;

import java.util.List;

/**
 * Данные для поста, который лайкнем
 * 
 * @author lomovtsevrs
 */
public class Post {
	
	long postId;
	long ownerId;
	String ownerName;
	boolean isLiked;
	private long date;

	List<Comment> comments;

	public Post(String ownerName, long source, long post, boolean isLiked, long date) {
		this.ownerName = ownerName;
		ownerId = source;
		postId = post;
		this.isLiked = isLiked;
		this.date = date;
	}


	public long getOwnerId() {
		return ownerId;
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		return "user: " + ownerName + "\t|\tid: " + postId + " \n";
	}

	public long getDate() {
		return date;
	}

}
