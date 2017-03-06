package ru.coutvv.vkliker.orm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by lomovtsevrs on 03.03.2017.
 */
@Entity
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long time;
    private String author;

    public LikePost() {};

    public LikePost(String author) {
        this.time = System.currentTimeMillis();
        this.author = author;
    }

    public LikePost(long time, String author) {
        this.time = time;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return author + " | " + new Date(time);
    }
}
