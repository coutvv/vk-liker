package ru.coutvv.vkliker.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import ru.coutvv.vkliker.api.entity.Comment;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.orm.LikePostRepository;
import ru.coutvv.vkliker.orm.entity.LikePost;

public class Liker {
	
	private final UserActor actor;
	private final VkApiClient vk;
	private LikePostRepository lpr = new LikePostRepository();
	
	public Liker(UserActor actor, VkApiClient vk) {
		this.actor = actor;
		this.vk = vk;
	}


	public void like(Item post) {
		String script = "return API.likes.add({\"type\": \"post" + "\", \"owner_id\": " + post.getSourceId() + ", "
				+ "\"item_id\" : " + post.getPostId() + "});";
		if(post.getLikes().getCanLike() == 0) return;
		try {
			vk.execute().code(actor, script).execute();
		} catch (ApiException | ClientException e) {
			System.out.println("Не смоглось залайкоть! D:]");
		}
	}

	public void likeAndSave(Item post, String author) {
		like(post);
		LikePost lp = new LikePost(author);
		lpr.saveLikePost(lp);
	}
	
	/**
	 * Лайкаем комментарии к посту
	 * @param comment -- наша сущность
	 * @param idPostOwner -- ид обладателя поста!!!!111!
	 */
	public void like(Comment comment, long idPostOwner) {
		if(comment.isLiked()) return;//не спамим! ^_^
		String script = "return API.likes.add({\"type\": \"comment\"," + " \"owner_id\": " + idPostOwner + ", "
				+ "\"item_id\" : " + comment.getCommentId() + "});";
		try {
			vk.execute().code(actor, script).execute();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
	
}
