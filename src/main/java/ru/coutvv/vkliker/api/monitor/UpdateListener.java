package ru.coutvv.vkliker.api.monitor;

import java.util.List;

import ru.coutvv.vkliker.api.entity.Comment;
import ru.coutvv.vkliker.api.entity.Item;

public interface UpdateListener {

	void update(Item post, List<Comment> comments);
}
