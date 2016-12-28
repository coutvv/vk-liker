package ru.coutvv.vkliker.api.monitor;

import java.util.List;

import ru.coutvv.vkliker.api.Liker;
import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Item;
import ru.coutvv.vkliker.util.LagUtil;

public class LikeCommentListener implements UpdateListener {
	
	private Liker liker;
	public LikeCommentListener(Liker liker){
		this.liker = liker;
	}

	@Override
	public void update(Item post, List<Comment> comments) {
		for(Comment c : comments) {
			System.out.println("Comment : " + c.getText() + " was liked");
			liker.like(c, post.getSourceId());
			LagUtil.lag();
		}
	}

}
