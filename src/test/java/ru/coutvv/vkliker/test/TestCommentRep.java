package ru.coutvv.vkliker.test;

import java.io.IOException;
import java.util.List;

import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.api.entity.Comment;
import ru.coutvv.vkliker.api.repository.CommentRepository;

public class TestCommentRep {

	public static void main(String[] args) throws IOException {
		
		String filename = "app.properties";
		Factory fac = new Factory(filename);
		CommentRepository cr = fac.createCommentRepository();
		
		long ownerId = -30666517,
				itemId = 1449066;
		
		List<Comment> l2 = cr.getComments(ownerId, itemId);
		
//		System.out.println(l2);
		
	}
}
