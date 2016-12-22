package ru.coutvv.vkliker.test;

import java.io.IOException;
import java.util.List;

import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.api.Liker;
import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.repository.CommentRepository;

public class TestCommentRep {

	public static void main(String[] args) throws IOException {
		String filename = "app.properties";
		Factory fac = new Factory(filename);
		CommentRepository cr = fac.createCommentRepository();
		long ownerId = -30666517L,
			 postId = 1448478L;
		List<Comment> resp = cr.getComments(ownerId, postId);
		System.out.println(resp);
		
		Liker liker = fac.createPostLiker();
		liker.like(resp.get(0), ownerId);
	}
}
