package ru.coutvv.vkliker.api.repository;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

/**
 * Хранилище постов из новостной ленты
 * 
 * @author lomovtsevrs
 */
public class PostRepositoryImpl implements PostRepository {

	private UserActor actor;
	private VkApiClient vk;

	public PostRepositoryImpl(UserActor actor, VkApiClient vk) {
		this.actor = actor;
		this.vk = vk;
	}

	@Override
	public JsonElement getAtLast(int minutes) throws ClientException, ApiException {
		long time = System.currentTimeMillis()/1000 - (60 * minutes);
		String arg =  "\"start_time\" : \"" + time + "\",";
		String script = String.format(scriptTemplate, String.format(timeArgumentTemplate, time));
		JsonElement json = vk.execute().code(actor, script).execute();
		return json;
	}

	@Override
	public JsonElement getLast(int count) throws ClientException, ApiException {
		String script =  String.format(scriptTemplate, String.format(countArgumentTemplate, count));
		JsonElement json = vk.execute().code(actor, script).execute();
		return json;
	}

	private static final String scriptTemplate = "return API.newsfeed.get({\"return_banned\"  : 1, \"filters\" : \"post\", %s});";
	private static final String timeArgumentTemplate = "\"start_time\" : \"%s\",";
	private static final String countArgumentTemplate = "\"count\" : \"%s\",";

}
