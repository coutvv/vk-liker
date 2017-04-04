package ru.coutvv.vkliker.api;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.api.monitor.CommentMonitor;
import ru.coutvv.vkliker.api.monitor.LikeCommentListener;
import ru.coutvv.vkliker.api.repository.*;
import ru.coutvv.vkliker.notify.Logger;
import ru.coutvv.vkliker.util.Consts;
import ru.coutvv.vkliker.util.LagUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ru.coutvv.vkliker.util.Consts.formatter;

/**
 * Нужен для лайкания(а нафиг лайкер тогда?)
 *
 * @author coutvv
 */
public class NewsManagerImpl implements NewsManager {

    private final UserActor actor;
    private final VkApiClient vk;
    private final Liker liker;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    public NewsManagerImpl(UserActor actor, VkApiClient vkClient) {
        this.actor = actor;
        this.vk = vkClient;
        this.postRepository = new PostRepositoryImpl(actor, vk);
        this.liker = new Liker(actor, vk);
        this.commentRepository = new CommentRepositoryImpl(actor, vk);
    }

    @Override
    public Future likeLastPosts(int hours) {

        ExecutorService exec = Executors.newSingleThreadExecutor();

        Runnable task = () -> {
            ComplexFeedData cfd = null;
            try {
                cfd = new ComplexFeedData(postRepository.getAtLast(hours * 60));
            } catch (ClientException e) {
                e.printStackTrace();
            } catch (ApiException e) {
                e.printStackTrace();
            }

            //все запостившие пацаны
            final Map<Long, String> names = Stream.of(cfd.getProfiles(), cfd.getGroups())
                                                .map(Map::entrySet)
                                                .flatMap(Collection::stream)
                                                .collect(Collectors.toMap(Map.Entry::getKey, (e) -> e.getValue().toString()));

            for(Item item : cfd.getItems()) {
                if(item.getLikes().getCanLike() == 1 && item.getSourceId() != (long)actor.getId()) {//тип не лайкать свои посты
                    long ownerId = Math.abs(item.getSourceId());//тип у груп, чтоб тоже норм было
                    liker.likeAndSave(item, names.get(ownerId));
                    if(item.getSourceId() > 0) {
                        Logger.log("liked post by person: " + cfd.getProfiles().get(ownerId));
                    } else {
                        Logger.log("liked post by community: " + cfd.getGroups().get(ownerId));
                    }

                    LagUtil.lag();
                }
            }
            exec.shutdown();
        };

        Future result = exec.submit(task);
        return result;
    }

    @Override
    public Future scheduleLike(int period) {
        Runnable task = () -> {
            Logger.log("[ lets like my feed forever ]");
            for (;;) {
                int hours = period / 60 + 1;// период за который получим
                // новости
                try {
                    Future future = likeLastPosts(hours);
                    future.get();//lock thread
                } catch (Exception e) {
                    Logger.log("[ Can't reach some feauture ]" + e.getMessage());
                }
                Logger.log("[ wait ] " + formatter.format(LocalDateTime.now()));

                LagUtil.lag(period * 60 * 1000);
            }
        };
        ExecutorService exec = Executors.newSingleThreadExecutor();
        return exec.submit(task);
    }

    @Override
    public Future commentWatching(int minutes, long timeout) {
        Runnable likeCommentTask = () -> {
            CommentMonitor cm = new CommentMonitor(liker, commentRepository, timeout, minutes);
            LikeCommentListener cll = new LikeCommentListener(liker);
            cm.addListener(cll);
            System.out.println("<<Watching for comments!>>");
            for (;;) {
                System.out.println("[ Comment session ] this ended at " + new Date());
                List<Item> posts = null;
                try {
                    posts = ComplexFeedData.parseItems(postRepository.getAtLast(minutes));
                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                for (Item post : posts) {
                    cm.addToWatch(post);
                }
                LagUtil.lag(minutes * 60 * 1000);
            }
        };

        ExecutorService exec = Executors.newSingleThreadExecutor();
        return exec.submit(likeCommentTask);
    }


}
