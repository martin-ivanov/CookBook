package com.cookbook.persistence.dao;

import com.cookbook.persistence.entity.SubscriptionEntity;

public interface SubscriptionDAO {
	

	public abstract SubscriptionEntity unsubcribe (SubscriptionEntity subscription);
	
	public abstract SubscriptionEntity subcribe (SubscriptionEntity subscription);
	
	
}
