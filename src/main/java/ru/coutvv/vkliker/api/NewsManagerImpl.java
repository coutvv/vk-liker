package ru.coutvv.vkliker.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import ru.coutvv.vkliker.api.repository.CommentRepositoryImpl;
import ru.coutvv.vkliker.api.repository.PostRepositoryImpl;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author coutvv
 */
public class NewsManagerImpl implements NewsManager {

    private final UserActor actor;
    private final VkApiClient vk;
    private final Liker liker;

    private final PostRepositoryImpl postRepository;
    private final CommentRepositoryImpl commentRepositoryImpl;

    private Map<Long, String> names;

    public NewsManagerImpl(UserActor actor, VkApiClient vkClient) {

        this.actor = actor;
        this.vk = vkClient;

        postRepository = new PostRepositoryImpl(actor, vk);
        liker = new Liker(actor, vk);
        commentRepositoryImpl = new CommentRepositoryImpl(actor, vk);
    }

    @Override
    public ExecutorService likeLastPosts(int hours) {
        return null;
    }

    @Override
    public ExecutorService scheduleLike(int period) {
        return null;
    }

    @Override
    public ExecutorService commentWatching(int minutes, long timeout) {
        return null;
    }
}
