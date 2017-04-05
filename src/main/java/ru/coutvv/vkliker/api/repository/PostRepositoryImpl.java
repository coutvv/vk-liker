package ru.coutvv.vkliker.api.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.coutvv.vkliker.util.LagUtil;

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
		String script = String.format(SCRIPT_TEMPLATE, String.format(TIME_ARGUMENT, time));
		JsonElement json = vk.execute().code(actor, script).execute();
		return json;
	}

	@Override
	public JsonElement getLast(int count) throws ClientException, ApiException {
		String script =  String.format(SCRIPT_TEMPLATE, String.format(COUNT_ARGUMENT_TEMPLATE, count));
		JsonElement json = vk.execute().code(actor, script).execute();
		return json;
	}

	@Override
	public JsonElement getWall(String userId) throws ClientException, ApiException {
		final String GET_WALL_SCRIPT = "return API.wall.get({\"owner_id\"  : %1$s, \"count\" : 100, \"filter\" : \"owner\", \"offset\" : %2$d});";

		JsonElement result = vk.execute().code(actor, String.format(GET_WALL_SCRIPT, userId, 0)).execute();
		int count = result.getAsJsonObject().get("count").getAsInt();
		JsonArray resultArray = result.getAsJsonObject().get("items").getAsJsonArray();
		for(int offset = 100; offset <= count; offset+=100) {
			JsonElement temp = vk.execute().code(actor, String.format(GET_WALL_SCRIPT, userId, offset)).execute();
			JsonArray items = temp.getAsJsonObject().get("items").getAsJsonArray();
			resultArray.addAll(items);
			LagUtil.lag(500);
		}
		return result;
	}

	private static final String SCRIPT_TEMPLATE = "return API.newsfeed.get({\"return_banned\"  : 1, \"filters\" : \"post\", %s});";
	private static final String TIME_ARGUMENT = "\"start_time\" : \"%s\",";
	private static final String COUNT_ARGUMENT_TEMPLATE = "\"count\" : \"%s\",";

}
