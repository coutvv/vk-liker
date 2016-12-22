package ru.coutvv.vkliker.data.repository;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import ru.coutvv.vkliker.data.entity.Comment;

public class LikesRepository extends Repository {

	public LikesRepository(UserActor actor, VkApiClient vk) {
		super(actor, vk);
	}
	
	public JsonElement getLikes(long ownerId, long itemId, String type) {
		String script = "return API.likes.getList({" +
				"\"type\" : " + "\""+ type+ "\"" + "," +
				"\"owner_id\" : " + ownerId + "," +
				"\"item_id\" : " + itemId + "," +
				"});";
		JsonElement json = null;
		try {
			json = vk.execute().code(actor, script).execute();
			System.out.println(json);
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public boolean hasLikedComment(long ownerId, long itemId) {
		boolean result = isLiked(ownerId, itemId, "comment");
		return result;
	}
	
	public boolean hasLiked(Comment comment) {
		boolean result = isLiked(comment.getPostOwnerId(), comment.getCommentId(), "comment");
		return result;
	}
	
	private boolean isLiked(long ownerId, long itemId, String type) {
		String script = "return API.likes.isLiked({" +
				"\"type\" : " + "\""+ type+ "\"" + "," +
				"\"owner_id\" : " + ownerId + "," +
				"\"item_id\" : " + itemId + "," +
				"});";
		JsonElement json = null;
		try {
			json = vk.execute().code(actor, script).execute();
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Чот не фортануло с лайканием, сорян");
		}
		return json.getAsJsonObject().get("liked").getAsInt() == 1;
	}

}
