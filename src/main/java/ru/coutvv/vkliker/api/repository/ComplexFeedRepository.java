package ru.coutvv.vkliker.api.repository;

import ru.coutvv.vkliker.api.entity.ComplexFeedData;

public interface ComplexFeedRepository {

	public ComplexFeedData getFeedLastMinutes(int minutes);
	public ComplexFeedData getFeedLastCount(int count);
	
}
