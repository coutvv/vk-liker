package ru.coutvv.vkliker.api.entity;

import com.google.gson.annotations.SerializedName;
import com.vk.api.sdk.objects.base.LikesInfo;

/**
 * Новость, фотка или другая херь
 */
public class Item {

	@SerializedName("type") 
	private String type;
	@SerializedName("source_id") 
	private Long sourceId;
	@SerializedName("date") 
	private Long date;
	@SerializedName("post_id") 
	private Long postId;
	@SerializedName("post_type") 
	private String postType;
	@SerializedName("text") 
	private String text;
	@SerializedName("signer_id") 
	private Long signerId;
	
	private LikesInfo likes;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getDate() {
		return date;
	}

	public LikesInfo getLikes() {
		return likes;
	}

	public void setLikes(LikesInfo likes) {
		this.likes = likes;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getSignerId() {
		return signerId;
	}

	public void setSignerId(Long signerId) {
		this.signerId = signerId;
	}

	public String toString() {
		return type + " : " + text + " \n";
	}

	
}
