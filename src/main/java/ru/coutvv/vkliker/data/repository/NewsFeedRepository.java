package ru.coutvv.vkliker.data.repository;

import ru.coutvv.vkliker.data.repository.data.ComplexFeedData;

public interface NewsFeedRepository {

	public ComplexFeedData getFeedLastMinutes(int minutes);
	public ComplexFeedData getFeedLastCount(int count);
	
}
