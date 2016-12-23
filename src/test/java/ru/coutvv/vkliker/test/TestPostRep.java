package ru.coutvv.vkliker.test;

import java.io.IOException;
import java.util.List;

import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.data.entity.Post;
import ru.coutvv.vkliker.data.repository.PostRepository;

public class TestPostRep {

	static PostRepository cr;
	
	public static void main(String[] args) throws IOException {
		String filename = "app.properties";
		Factory fac = new Factory(filename);
		cr = fac.createPostRepository();
		
		List<Post> posts = cr.getLastPosts(1);
		System.out.println(posts);
	}
}
