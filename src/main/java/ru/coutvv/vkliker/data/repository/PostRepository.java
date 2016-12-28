package ru.coutvv.vkliker.data.repository;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import ru.coutvv.vkliker.data.entity.Group;
import ru.coutvv.vkliker.data.entity.Item;
import ru.coutvv.vkliker.data.entity.Profile;
import ru.coutvv.vkliker.data.repository.data.ComplexFeedData;

/**
 * Отвечает за получение и парсинг данных
 * 
 * @author lomovtsevrs
 */
public class PostRepository extends Repository implements ComplexFeedRepository {

	public PostRepository(UserActor actor, VkApiClient vk) {
		super(actor, vk);
	}

	@Override
	public ComplexFeedData getFeedLastMinutes(int minutes) {
		long time = System.currentTimeMillis()/1000 - 60 * minutes;//секунд с начала UNIX эпохи минус minutes минут
		String script = configurateScript(time, null);
		JsonElement json = runScript(script);
		return new ComplexFeedData(parseItems(json), parseProfiles(json), parseGroups(json));
	}

	@Override
	public ComplexFeedData getFeedLastCount(int count) {
		String script = configurateScript(null, count);
		JsonElement json = runScript(script);
		return new ComplexFeedData(parseItems(json), parseProfiles(json), parseGroups(json));
	}
	/**
	 * Получение постов за последние hours часов
	 * @param hours
	 * @return
	 */
	public List<Item> getLastPosts(int hours) {
		return getLastPostsInMin(hours*60);
	}
	
	/**
	 * Получение постов за последние minutes минут
	 * @param hours
	 * @return
	 */
	public List<Item> getLastPostsInMin(int minutes) {
		long time = System.currentTimeMillis()/1000 - (60 * minutes);
		String script = configurateScript(time, null);
		return parseItems(runScript(script));
	}
	
	/**
	 * Получение последних постов в количестве count штук
	 * @param count
	 * @return
	 */
	public List<Item> getLastCountPosts(int count) {
		String script = configurateScript(null, count);
		return parseItems(runScript(script));
	}
	

	public JsonElement getFeedItems(Long startTime) {
		String script = "return API.newsfeed.get({" +
				"\"start_time\" : \"" + startTime + "\",  " +
				"\"return_banned\" : \"" + 1 +  "\",  " +
				"});";
		return runScript(script);
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

	
	/**
	 * Парсинг jsona  
	 * @param response -- список постов 
	 * @return
	 */
	private List<Item>  parseItems(JsonElement response) {
		JsonElement json = response.getAsJsonObject().get("items");
		Item[] result = new Gson().fromJson(json, Item[].class);
		 return Arrays.asList(result);
	}
	
	/**
	 * Парсинг профилей, владельцев постов
	 * @param response
	 * @return
	 */
	private List<Profile> parseProfiles(JsonElement response) {
		JsonElement json = response.getAsJsonObject().get("profiles");
		Profile[] result = new Gson().fromJson(json, Profile[].class);
		 return Arrays.asList(result);
	}
	
	/**
	 * Парсинг групп, владельцев постов
	 * @param response
	 * @return
	 */
	private List<Group> parseGroups(JsonElement response) {
		JsonElement json = response.getAsJsonObject().get("groups");
		Group[] result = new Gson().fromJson(json, Group[].class);
		return Arrays.asList(result);
	}
}
