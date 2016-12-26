package ru.coutvv.vkliker.api.monitor;

import java.util.List;

import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Post;

public interface UpdateListener {

	void update(Post post, List<Comment> comments);
}
