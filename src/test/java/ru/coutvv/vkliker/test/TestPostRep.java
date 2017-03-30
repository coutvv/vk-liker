package ru.coutvv.vkliker.test;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonElement;

import ru.coutvv.vkliker.Factory;
import ru.coutvv.vkliker.api.entity.Item;
import ru.coutvv.vkliker.api.entity.Profile;
import ru.coutvv.vkliker.api.repository.PostRepository;
import ru.coutvv.vkliker.api.entity.ComplexFeedData;

public class TestPostRep {

	static PostRepository cr;
	
	public static void main(String[] args) throws IOException {
		String filename = "app.properties";
		Factory fac = new Factory(filename);
		cr = fac.createPostRepository();
		
		JsonElement json = cr.getFeedItems(System.currentTimeMillis() -(60*60*1000));
		List<Item> itm = cr.getLastPostsInMin(60*2);
		System.out.println(itm);
		
		ComplexFeedData cfd = cr.getFeedLastMinutes(120);
		Profile p = cfd.getProfiles().get(8213324l);
		System.out.println(p);
	}
}
