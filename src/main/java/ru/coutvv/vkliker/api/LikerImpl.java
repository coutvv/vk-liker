package ru.coutvv.vkliker.api;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import ru.coutvv.vkliker.data.Post;

/**
 * Реализация лайкера посредством vk api sdk
 * 
 * @author lomovtsevrs
 */
public class LikerImpl implements Liker {

	private final UserActor actor;
	private final VkApiClient vk;
	private final int userId;
	
	public LikerImpl(int userId, String token) {
		actor = new UserActor(userId, token);
		this.userId = userId;
		TransportClient tc = HttpTransportClient.getInstance();
		vk = new VkApiClient(tc, new Gson());
	}
	
	public List<Post> getLastNews(int count) {
		String getNewsScript = "return API.newsfeed.get({\"count\": " + count + "});";
		JsonElement response = null;
		try {
			response = vk.execute().code(actor, getNewsScript).execute();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return response == null ? new ArrayList<Post>() : parseJson(response);
	}
	
	private List<Post>  parseJson(JsonElement response) {
		List<Post> result  = new ArrayList<Post>();
		JsonArray items = response.getAsJsonObject().get("items").getAsJsonArray();
		for(int i = 0; i < items.size(); i++ ) {
			JsonObject item = items.get(i).getAsJsonObject();
			
			String type = item.get("type").getAsString();
			
			printPost(item);
			if(canLike(item) && type.equalsIgnoreCase("post")) {
				int source_id = item.get("source_id").getAsInt();
				int post_id = item.get("post_id").getAsInt();
				if(source_id != userId ) result.add(new Post(type, source_id, post_id)); //условие чтобы не лайкать свои комменты
			}
		}
		return result;
	}
	
	private boolean canLike(JsonObject item) {
		boolean result = false;
		try {
			result =  item.get("likes").getAsJsonObject().get("can_like").getAsInt() == 1;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Печатаем чо за пост
	 * @param item
	 */
	private void printPost(JsonObject item) {
		System.out.println("<[BEGINPOST]>");
		try { 
			
			System.out.println(item);
		} catch(Exception e) {
			System.out.println(item.get("type").getAsString());
		}
		System.out.println("<[ENDOFPOST]>");
		System.out.println();
	}
	
	public void likePost(Post post) {
		String script = "return API.likes.add({\"type\": \""+post.getType()+
						"\", \"owner_id\": "+ post.getSourceId() + ", "+
						"\"item_id\" : " + post.getPostId() + "});";
		try {
			vk.execute().code(actor, script).execute();
			likeCount++;
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	private int likeCount = 0;

	public int getLikeCount() {
		return likeCount;
	}
	
}
