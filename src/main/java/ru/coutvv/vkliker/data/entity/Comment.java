package ru.coutvv.vkliker.data.entity;

import java.util.Date;

import com.google.gson.JsonObject;

public class Comment {

	/**
	 * идшники поста и его хозяина
	 */
	private final long postId;
	private final long postOwnerId;
	
	/**
	 * идшники комментария и его хозяина
	 */
	private final long ownerId;
	private final long commentId;
	private final long date;
	private final String text;
	
	private final boolean isLiked;
	
	public Comment(JsonObject json, long postId, long postOwnerId) {
		this.ownerId = json.get("from_id").getAsLong();
		this.commentId = json.get("id").getAsLong();
		this.date = json.get("date").getAsLong();
		this.text = json.get("text").getAsString();
		this.postId = postId;
		this.postOwnerId = postOwnerId;
		this.isLiked = json.get("likes").getAsJsonObject().get("can_like").getAsInt() == 0;
	}
	
	public String toString() {
		String result = "\n[COMMENT] : " + commentId + "\n" +
						"owner_id : \t" + ownerId + "\n" +
						"text : \t" + text + "\n" +
						"when : \t" + new Date(date*1000)+ "\n";
		return result;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public long getCommentId() {
		return commentId;
	}

	public long getDate() {
		return date;
	}

	public String getText() {
		return text;
	}

	public long getPostId() {
		return postId;
	}

	public long getPostOwnerId() {
		return postOwnerId;
	}

	public boolean isLiked() {
		return isLiked;
	}
}
