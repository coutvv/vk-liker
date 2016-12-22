package ru.coutvv.vkliker.test;

import java.io.IOException;
import java.util.List;

import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.data.entity.Comment;
import ru.coutvv.vkliker.data.repository.LikesRepository;
import ru.coutvv.vkliker.util.LagUtil;

public class TestLikeRep {

	public static void main(String[] args) throws IOException {
		String filename = "app.properties";
		Factory fac = new Factory(filename);
		long ownerId = -30666517,
				itemId = 103132;
//		fac.createLikesRepository().getLikes(ownerId, itemId);
		LikesRepository lr = fac.createLikesRepository();
//		System.out.println(lr.hasLikedComment(ownerId, itemId));
		List<Comment> l = fac.createCommentRepository().getComments(ownerId, 1448651);
//		System.out.println(l);
		for(Comment c : l) {
			System.out.println(lr.hasLiked(c));
			LagUtil.lag();
		}
	}
}
