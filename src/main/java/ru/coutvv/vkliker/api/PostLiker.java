package ru.coutvv.vkliker.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import ru.coutvv.vkliker.data.Comment;
import ru.coutvv.vkliker.data.Post;

public class PostLiker {
	
	private final UserActor actor;
	private final VkApiClient vk;
	
	public PostLiker(UserActor actor, VkApiClient vk) {
		this.actor = actor;
		this.vk = vk;
	}

	public void like(Post post) {
		String script = "return API.likes.add({\"type\": \"post" + "\", \"owner_id\": " + post.getSourceId() + ", "
				+ "\"item_id\" : " + post.getPostId() + "});";
		if(post.isLiked()) return;
		try {
			vk.execute().code(actor, script).execute();
			System.out.println("liked post of " + post.getOwnerName());
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Лайкаем комментарии к посту
	 * @param comment -- наша сущность
	 * @param idPostOwner -- ид обладателя поста!!!!111!
	 */
	public void like(Comment comment, long idPostOwner) {
		String script = "return API.likes.add({\"type\": \"comment\"," + " \"owner_id\": " + idPostOwner + ", "
				+ "\"item_id\" : " + comment.getCommentId() + "});";
		System.out.println(script);
		try {
			vk.execute().code(actor, script).execute();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
