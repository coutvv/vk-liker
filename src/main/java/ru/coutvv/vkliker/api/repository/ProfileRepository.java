package ru.coutvv.vkliker.api.repository;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.coutvv.vkliker.api.entity.Profile;

/**
 * @author coutvv
 */
public interface ProfileRepository {

    Profile getProfile(String id) throws ClientException, ApiException;
}
