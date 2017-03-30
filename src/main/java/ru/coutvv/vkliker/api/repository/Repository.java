package ru.coutvv.vkliker.api.repository;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;

public abstract class Repository {

	protected final UserActor actor;
	protected final VkApiClient vk;
	
	public Repository(UserActor actor, VkApiClient vk) {
		this.actor = actor;
		this.vk = vk;
	}
}
