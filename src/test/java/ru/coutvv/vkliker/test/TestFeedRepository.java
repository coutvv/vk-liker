package ru.coutvv.vkliker.test;

import java.io.IOException;

import com.google.gson.JsonElement;

import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.data.repository.PostRepository;

public class TestFeedRepository {

	
	public static void main(String[] args) throws IOException {
		Factory fac = new Factory("app.properties"); 
		PostRepository pr = fac.createPostRepository();

		long time = (System.currentTimeMillis() - (60 * 60*1000))/1000;
		JsonElement resp = pr.getFeedItems(time);
		System.out.println(resp);
		
	}
}
