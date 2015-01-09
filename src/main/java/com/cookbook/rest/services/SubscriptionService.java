package com.cookbook.rest.services;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.SubscriptionEntity;


/**
 * 
 * @author martin.ivanov
 *
 */
public interface SubscriptionService {
	
	public abstract SubscriptionEntity unsubcribe (Long userId, Long categoryId) throws AppException;
	
	public abstract SubscriptionEntity subcribe (Long userId, Long categoryId) throws AppException;
	

}