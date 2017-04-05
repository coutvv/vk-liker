package ru.coutvv.vkliker.api.repository;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.coutvv.vkliker.api.entity.Profile;

/**
 * @author coutvv
 */
public class ProfileRepositoryImpl implements ProfileRepository {

    private UserActor actor;
    private VkApiClient vk;

    public ProfileRepositoryImpl(UserActor actor, VkApiClient vk) {
        this.actor = actor;
        this.vk = vk;
    }

    @Override
    public Profile getProfile(String id) throws ClientException, ApiException {

        final String GET_WALL_SCRIPT = "return API.users.get({\"user_ids\"  : \"%s\"});";

        JsonElement res = vk.execute().code(actor, String.format(GET_WALL_SCRIPT, id)).execute();
        System.out.println(res);
        JsonElement json = res.getAsJsonArray().get(0).getAsJsonObject();
        Profile result = new Gson().fromJson(json, Profile.class);
        System.out.println(result.toString());
        return result;
    }

}
