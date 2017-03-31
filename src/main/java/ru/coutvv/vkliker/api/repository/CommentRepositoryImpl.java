package ru.coutvv.vkliker.api.repository;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import ru.coutvv.vkliker.api.entity.Comment;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.util.LagUtil;

/**
 * Хранилище комментариев
 * 
 * @author lomovtsevrs
 */
public class CommentRepositoryImpl {

	UserActor actor;
	VkApiClient vk;

	public CommentRepositoryImpl(UserActor actor, VkApiClient vk) {
		this.actor = actor;
		this.vk = vk;
	}

	public List<Comment> getComments(Item post) {
		return getComments(post, 0);
	}
	
	public List<Comment> getComments(Item post, int startFrom) {
		return getComments(post.getSourceId(), post.getPostId(), startFrom);
	}
	
	
	public List<Comment> getComments(long postOwnerId, long postId) {
		return getComments(postOwnerId, postId, 0);
	}
	
	public List<Comment> getComments(long postOwnerId, long postId, int start) {
		List<Comment> result = new ArrayList<>();
		int count = start;

		int maxCount = -1;
		do {
			JsonElement data = getCommentsJson(postOwnerId, postId, 100, count);
			if(maxCount == -1) maxCount = data.getAsJsonObject().get("count").getAsInt();
			result.addAll(parseJson(data, postId, postOwnerId));
			count += 100;
			LagUtil.lag();//не палимся
		} while(count < maxCount);
		return result;
	}

	private static final String scriptTemplate =
			"return API.wall.getComments({" +
			"\"owner_id\" : %1$d," +
			"\"post_id\" : %2$d," +
			"\"offset\" : %3$d," +
			"\"count\" : %4$d," +
			"\"need_likes\" : 1,});";
	
	private JsonElement getCommentsJson(long ownerId, long postId, int count, int offset) {
		String script = String.format(scriptTemplate, ownerId, postId, offset, count);
		JsonElement result = null;
		try {
			result = vk.execute().code(actor, script).execute();
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Не удалось получить JSON-объект комментариев" + script);
		}
		return result;
	}
	
	private List<Comment> parseJson(JsonElement json, long postId, long ownerId) {
		List<Comment> result = new ArrayList<>();
		JsonArray comments = json.getAsJsonObject().get("items").getAsJsonArray();
		for(int i = 0; i < comments.size(); i++) {
			JsonObject com = comments.get(i).getAsJsonObject();
			result.add(new Comment(com, postId, ownerId));
		}
		return result;
	}

}
