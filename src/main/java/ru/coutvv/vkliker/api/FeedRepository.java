package ru.coutvv.vkliker.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import ru.coutvv.vkliker.data.Post;

/**
 * Отвечает за получение и парсинг данных
 * 
 * @author lomovtsevrs
 */
public class FeedRepository {
	
	private final UserActor actor;
	private final VkApiClient vk;
	
	public FeedRepository(UserActor actor, VkApiClient vk) {
		this.actor = actor;
		this.vk = vk;
	}

	//час в миллисекундах
	private static final long HOUR_AGO = 60*60*1000;

	
	/**
	 * Получение постов за последние hours часов
	 * @param hours
	 * @return
	 */
	public List<Post> getLastPosts(int hours) {
		long time = (System.currentTimeMillis() - HOUR_AGO * hours)/1000;
		String script = configurateScript(time, null);
		return parsePosts(runScript(script));
	}

	
	/**
	 * Получение последних постов в количестве count штук
	 * @param count
	 * @return
	 */
	public List<Post> getLastCountPosts(int count) {
		String script = configurateScript(null, count);
		return parsePosts(runScript(script));
	}

	/**
	 * Парсинг jsona  
	 * @param response -- список постов 
	 * @return
	 */
	private List<Post>  parsePosts(JsonElement response) {
		List<Post> result  = new ArrayList<Post>(); 
		JsonArray items = response.getAsJsonObject().get("items").getAsJsonArray();
		
		Map<Long, String> friends = parsePersons(response);
		Map<Long, String> groups = parseGroups(response);
		for(int i = 0; i < items.size(); i++ ) {
			JsonObject item = items.get(i).getAsJsonObject();
			long sourceId = item.get("source_id").getAsLong();
			long postId = item.get("post_id").getAsInt();
			
			boolean isLike = item.get("likes").getAsJsonObject().get("can_like").getAsInt() == 0; //залайкано?
			String ownerName = (sourceId > 0) ? friends.get(sourceId) : groups.get(-sourceId);
			if(sourceId != actor.getId() ) {//условие чтобы не лайкать свои комменты
				result.add(new Post(ownerName, sourceId, postId, isLike)); 
			}

		}
		return result;
	}
	
	/**
	 * Получаем мапу с пользователями
	 * @param resp
	 * @return
	 */
	private Map<Long, String> parsePersons(JsonElement resp) {
		Map<Long, String> result = new HashMap<>();
		JsonArray profiles = resp.getAsJsonObject().get("profiles").getAsJsonArray();
		for(int i = 0; i < profiles.size(); i++) {
			JsonObject profile = profiles.get(i).getAsJsonObject();
			String name = profile.get("first_name").getAsString();
			String lastname = profile.get("last_name").getAsString();
			long id = profile.get("id").getAsLong();
			result.put(id, lastname + " " + name);
		}
		return result;
	}
	
	/**
	 * Парсим и получаем мапу с группами
	 * @param resp
	 * @return
	 */
	private Map<Long, String> parseGroups(JsonElement resp) {
		Map<Long, String> result = new HashMap<>();
		JsonArray groups = resp.getAsJsonObject().get("groups").getAsJsonArray();
		for(int i = 0; i < groups.size(); i++) {
			JsonObject group = groups.get(i).getAsJsonObject();
			String name = group.get("name").getAsString();
			long id = group.get("id").getAsLong();
			result.put(id, name);
		}
		return result;
	}
	
	/**
	 * Запускаем сценария получения json-объекта с постами
	 * @param script
	 * @return
	 */
	private JsonElement runScript(String script) {
		JsonElement response = null;
		try {
			response = vk.execute().code(actor, script).execute();
		} catch (ApiException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * Конфигуруем запрос 
	 * @param startTime
	 * @param count
	 * @return
	 */
	private String configurateScript(Long startTime, Integer count) {
		String script = "return API.newsfeed.get({" +
				( startTime == null ? "" : "\"start_time\" : \"" + startTime + "\",  " ) +
				( count     == null ? "" : "\"count\" : \"" + count +  "\",  " ) +
				"\"return_banned\" : \"" + 1 +  "\",  "+
				"\"filters\" : \"" + "post" +  "\",  "+
				"});";
		
		return script;
	}

}
