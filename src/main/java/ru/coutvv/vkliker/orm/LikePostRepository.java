package ru.coutvv.vkliker.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.coutvv.vkliker.orm.entity.LikePost;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class LikePostRepository {

    public LikePost saveLikePost(LikePost likePost) {
        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.save(likePost);
            tx.commit();
        }
        return likePost;
    }

    /**
     * Получение количества лайков за всё время
     * @return
     */
    public long getCountLike() {
        try(Session session = SessionUtil.getSession()) {
            List<LikePost> res =session.createQuery("from LikePost", LikePost.class).getResultList();
            return res.size();
        }
    }

    private static final long DAY_TIME = 24 * 60 * 60 * 1000;
    /**
     * Количество лайков за последние сутки
     */
    public long getCountLikePerDay() {
        try(Session session = SessionUtil.getSession()) {
            long time = System.currentTimeMillis() - DAY_TIME;
            List<LikePost> res = session.createQuery("from LikePost l where l.time > " + time, LikePost.class).getResultList();
            return res.size();
        }
    }
}
