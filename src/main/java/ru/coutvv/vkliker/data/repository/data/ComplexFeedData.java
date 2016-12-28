package ru.coutvv.vkliker.data.repository.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.coutvv.vkliker.data.entity.Group;
import ru.coutvv.vkliker.data.entity.Item;
import ru.coutvv.vkliker.data.entity.Profile;

public class ComplexFeedData {

	private List<Item> items;
	private Map<Long, Profile> profiles;
	private Map<Long, Group> groups;
	
	public ComplexFeedData(List<Item> items, List<Profile> profs, List<Group> groups) {
		this.items = items;
		this.profiles = new HashMap<>();
		for(Profile prof : profs) {
			profiles.put(prof.getId(), prof);
		}
		this.groups = new HashMap<>();
		for(Group g : groups) {
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
	
	
}
