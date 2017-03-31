package ru.coutvv.vkliker.api.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import ru.coutvv.vkliker.api.entity.Group;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.api.entity.Profile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Объект парсящий json новостей от вконтакта
 */
public class ComplexFeedData {

	/**
	 * Посты пользователей и групп
	 */
	private List<Item> items;
	/**
	 * Профили пользователей
	 */
	private Map<Long, Profile> profiles;
	/**
	 * Профили групп
	 */
	private Map<Long, Group> groups;
	
	public ComplexFeedData(JsonElement json) {
		this.items = parseItems(json);
		this.profiles = new HashMap<>();
		for(Profile prof : parseProfiles(json)) {
			profiles.put(prof.getId(), prof);
		}
		this.groups = new HashMap<>();
		for(Group g : parseGroups(json)) {
			this.groups.put(g.getId(), g);
		}
	}
	
	public List<Item> getItems() {
		return items;
	}
	public Map<Long, Profile> getProfiles() {
		return profiles;
	}
	public Map<Long, Group> getGroups() {
		return groups;
	}


	/**
	 * Парсинг jsona
	 * @param response -- список постов
	 * @return
	 */
	public static List<Item>  parseItems(JsonElement response) {
		JsonElement json = response.getAsJsonObject().get("items");
		Item[] result = new Gson().fromJson(json, Item[].class);
		return Arrays.asList(result);
	}

	/**
	 * Парсинг профилей, владельцев постов
	 * @param response
	 * @return
	 */
	public static List<Profile> parseProfiles(JsonElement response) {
		JsonElement json = response.getAsJsonObject().get("profiles");
		Profile[] result = new Gson().fromJson(json, Profile[].class);
		return Arrays.asList(result);
	}

	/**
	 * Парсинг групп, владельцев постов
	 * @param response
	 * @return
	 */
	public static List<Group> parseGroups(JsonElement response) {
		JsonElement json = response.getAsJsonObject().get("groups");
		Group[] result = new Gson().fromJson(json, Group[].class);
		return Arrays.asList(result);
	}
}
