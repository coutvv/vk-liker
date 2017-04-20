package ru.coutvv.vkliker.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.coutvv.vkliker.api.entity.Comment;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.orm.LikePostRepository;
import ru.coutvv.vkliker.orm.entity.LikePost;
import ru.coutvv.vkliker.util.LagUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Liker {

    private static final Log log = LogFactory.getLog(Liker.class);

	private final UserActor actor;
	private final VkApiClient vk;
	private LikePostRepository lpr = new LikePostRepository();
	
	public Liker(UserActor actor, VkApiClient vk) {
		this.actor = actor;
		this.vk = vk;
	}

	/**
	 * лайкание без сохранения
	 * @param items
	 */
	public void like(List<Item> items) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Runnable task = () -> {
			for(Item item : items) {
				like(item);
				LagUtil.lag();
			}
			executor.shutdown();
		};
		executor.submit(task);
	}


	private final String script = "return API.likes.add({" +
										"\"type\": \"post\", \"owner_id\": %1$d, " +
										"\"item_id\" : %2$d});";

	public boolean like(Item post) {
		Long id = post.getPostId();
		id = id == null ? post.getId() : id;
		Long authorId = post.getSourceId();
		authorId = authorId == null ? post.getOwnerId() : authorId;

		if(post.getLikes().getCanLike() == 0) return false;
		try {
			vk.execute().code(actor, String.format(script, authorId, id)).execute();

		} catch (ApiException | ClientException e) {
			System.out.println("Не смоглось залайкоть! D:] ПРобуем обойти");
			int time = 1000;
			while(time < 600000) {
				try {
					vk.execute().code(actor, String.format(script, authorId, id)).execute();
					return true;
				} catch (ApiException | ClientException e1) {
					LagUtil.lag(time);
					time *= 2;
				}
			}
		}
		return false;
	}

	public void likeAndSave(Item post, String author) {
		boolean isLiked = like(post);
		if(isLiked) {
			LikePost lp = new LikePost(author);
			lpr.saveLikePost(lp);
		}
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
		} catch (ApiException | ClientException e) {
		    log.error("error on like", e);
		}
    }
	
}
