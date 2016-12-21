package ru.coutvv.vkliker.data;

import java.util.Date;

import com.google.gson.JsonObject;

public class Comment {

	private final long ownerId;
	private final long commentId;
	private final long date;
	private final String text;
	
	public Comment(JsonObject json) {
		ownerId = json.get("from_id").getAsLong();
		commentId = json.get("id").getAsLong();
		date = json.get("date").getAsLong();
		text = json.get("text").getAsString();
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
}
