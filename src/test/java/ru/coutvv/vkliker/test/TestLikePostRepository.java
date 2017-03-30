package ru.coutvv.vkliker.test;

import ru.coutvv.vkliker.orm.LikePostRepository;
import ru.coutvv.vkliker.orm.entity.LikePost;

/**
 * Created by lomovtsevrs on 03.03.2017.
 */
public class TestLikePostRepository {

    public static void main(String[] args) {
        LikePostRepository lpr = new LikePostRepository();

        LikePost likePost = lpr.saveLikePost(new LikePost(System.currentTimeMillis(), "test"));
        System.out.println(likePost);
        System.out.println(lpr.getCountLike());
    }
}
