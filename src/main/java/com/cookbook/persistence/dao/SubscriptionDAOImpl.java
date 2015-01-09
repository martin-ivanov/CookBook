package com.cookbook.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cookbook.persistence.entity.SubscriptionEntity;

public class SubscriptionDAOImpl implements SubscriptionDAO {

	@PersistenceContext(unitName = "cookMateEntityManager")
	private EntityManager entityManager;

	@Override
	public SubscriptionEntity unsubcribe(SubscriptionEntity subscription) {
		entityManager.remove(subscription);
		entityManager.flush();
		return subscription;
	}

	@Override
	public SubscriptionEntity subcribe(SubscriptionEntity subscription) {
		entityManager.persist(subscription);
		entityManager.flush();
		return subscription;
	}
	
	





}
