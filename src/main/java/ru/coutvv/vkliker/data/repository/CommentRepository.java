package ru.coutvv.vkliker.data.repository;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Post;

/**
 * Хранилище комментариев
 * 
 * @author lomovtsevrs
 */
public class CommentRepository extends Repository {
	
	public CommentRepository(UserActor actor, VkApiClient vk) {
		super(actor, vk);
	}
	
	public List<Comment> getComments(long ownerId, long postId) {
		String script = "return API.wall.getComments({" +
				"\"owner_id\" : " + ownerId + "," +
				"\"post_id\" : " + postId + "," +
				"});";
		List<Comment> result = new ArrayList<Comment>();
		try {
			JsonElement json = vk.execute().code(actor, script).execute();
			System.out.println(json);
			JsonArray comments = json.getAsJsonObject().get("items").getAsJsonArray();
			for(int i = 0; i < comments.size(); i++) {
				JsonObject com = comments.get(i).getAsJsonObject();
				result.add(new Comment(com, postId, ownerId));
			}
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Comment> getComments(Post post) {
		return getComments(post.getSourceId(), post.getPostId());
	}
	
	public List<Comment> getCommentsWithOffset(long ownerId, long postId, int count, int offset) {
		String script = "return API.wall.getComments({" +
				"\"owner_id\" : " + ownerId + "," +
				"\"post_id\" : " + postId + "," +
				"\"offset\" : " + offset + "," +
				"\"count\" : " + count + "," +
				"});";
		List<Comment> result = new ArrayList<Comment>();
		try {
			JsonElement json = vk.execute().code(actor, script).execute();
			System.out.println(json);
			JsonArray comments = json.getAsJsonObject().get("items").getAsJsonArray();
			for(int i = 0; i < comments.size(); i++) {
				JsonObject com = comments.get(i).getAsJsonObject();
				result.add(new Comment(com, postId, ownerId));
			}
		} catch (ApiException | ClientException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
