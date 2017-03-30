package ru.coutvv.vkliker.api.monitor;

import java.util.ArrayList;
import java.util.List;

import ru.coutvv.vkliker.api.entity.Comment;
import ru.coutvv.vkliker.api.entity.Item;

public abstract class Observer {

	List<UpdateListener> listeners = new ArrayList<>();
	protected void digest(Item post, List<Comment> comments) {
		for(UpdateListener ul : listeners){
			ul.update(post, comments);
		}
	}
	
	public void addListener(UpdateListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(UpdateListener listener) {
		listeners.remove(listener);
	}
}
