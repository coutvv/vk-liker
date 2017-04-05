package ru.coutvv.vkliker.api;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.coutvv.vkliker.api.entity.Item;

import java.util.List;

/**
 * @author coutvv
 */
public interface WallManager {

    List<Item> getPosts(String userId) throws ClientException, ApiException;

    void likeWholeWall(String userId);
}
