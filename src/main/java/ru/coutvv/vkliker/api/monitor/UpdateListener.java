package ru.coutvv.vkliker.api.monitor;

import java.util.List;

import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Item;

public interface UpdateListener {

	void update(Item post, List<Comment> comments);
}
