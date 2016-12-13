package ru.coutvv.vkliker.api;

import com.google.gson.Gson;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import ru.coutvv.vkliker.data.Post;

public class PostLiker {
	
	private final UserActor actor;
	private final VkApiClient vk;
	
	public PostLiker(int userId, String token) {
		actor = new UserActor(userId, token);
		TransportClient tc = HttpTransportClient.getInstance();
		vk = new VkApiClient(tc, new Gson());
	}

	public void like(Post post) {
		String script = "return API.likes.add({\"type\": \"post" + "\", \"owner_id\": " + post.getSourceId() + ", "
				+ "\"item_id\" : " + post.getPostId() + "});";
		try {
			vk.execute().code(actor, script).execute();
			System.out.println("liked post of " + post.getOwnerName());
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
