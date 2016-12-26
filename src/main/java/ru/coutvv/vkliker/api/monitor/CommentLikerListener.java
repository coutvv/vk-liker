package ru.coutvv.vkliker.api.monitor;

import java.util.List;

import ru.coutvv.vkliker.api.Liker;
import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.entity.Post;
import ru.coutvv.vkliker.util.LagUtil;

public class CommentLikerListener implements UpdateListener {
	
	private Liker liker;
	public CommentLikerListener(Liker liker){
		this.liker = liker;
	}

	@Override
	public void update(Post post, List<Comment> comments) {
		for(Comment c : comments) {
			System.out.println("Comment : " + c.getText() + " was liked");
			liker.like(c, post.getOwnerId());
			LagUtil.lag();
		}
	}
	
	

}
