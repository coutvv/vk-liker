package ru.coutvv.vkliker.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.api.entity.Profile;
import ru.coutvv.vkliker.api.repository.PostRepository;
import ru.coutvv.vkliker.api.repository.PostRepositoryImpl;
import ru.coutvv.vkliker.api.repository.ProfileRepository;
import ru.coutvv.vkliker.util.LagUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author coutvv
 */
public class WallManagerImpl implements WallManager {

    private static final Log log = LogFactory.getLog(WallManagerImpl.class);

    private final UserActor actor;
    private final VkApiClient vk;
    private final PostRepository postRepository;
    private final Liker liker;
    private final ProfileRepository profileRepository;

    public WallManagerImpl(UserActor actor, VkApiClient vkClient, ProfileRepository profileRepository) {
        this.actor = actor;
        this.vk = vkClient;
        this.postRepository = new PostRepositoryImpl(actor, vk);
        this.liker = new Liker(actor, vk);
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Item> getPosts(String userId) throws ClientException, ApiException {
        JsonElement res = postRepository.getWall(userId);
        JsonElement json = res.getAsJsonObject().get("items");
        Item[] result = new Gson().fromJson(json, Item[].class);
        return Arrays.asList(result);
    }

    @Override
    public void likeWholeWall(String userId) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable task = () -> {
            try {
                Profile profile = profileRepository.getProfile(userId);

                List<Item> posts = getPosts(userId);
                for(Item item : posts) {
                    liker.likeAndSave(item, profile.toString());
                    LagUtil.lag();
                }
            } catch (ClientException | ApiException e) {
                log.debug("likeWholeWall", e);
            }
            executorService.shutdown();
        };
        executorService.submit(task);
    }
}
