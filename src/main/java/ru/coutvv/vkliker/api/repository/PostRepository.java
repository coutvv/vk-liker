package ru.coutvv.vkliker.api.repository;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.util.List;

public interface PostRepository {

	/**
	 * Получение json-ответа с данными о последних записях
	 * в новостной ленте
	 *
	 * @param minutes
	 * @return
	 * @throws ClientException
	 * @throws ApiException
	 */
	JsonElement getAtLast(int minutes) throws ClientException, ApiException;

	/**
	 * Получение json-объекта с данными о count записях в новостной
	 * ленте
	 *
	 * @param count
	 * @return
	 * @throws ClientException
	 * @throws ApiException
	 */
	JsonElement getLast(int count) throws ClientException, ApiException;

}
