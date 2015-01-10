package com.cookbook.persistence.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cookbook.persistence.entity.SubscriptionEntity;

public class SubscriptionDAOImpl implements SubscriptionDAO {

	@PersistenceContext(unitName = "cookMateEntityManager")
	private EntityManager entityManager;

	@Autowired
	CategoryDAO categoryDAO;

	@Override
	@Transactional
	public SubscriptionEntity unsubcribe(SubscriptionEntity subscription) {
		SubscriptionEntity subscriptionEntity = getSubscription(
				subscription.getCategoryId(), subscription.getUserId());

		entityManager.remove(subscriptionEntity);
		entityManager.flush();
		return subscriptionEntity;
	}

	@Override
	@Transactional
	public SubscriptionEntity subcribe(SubscriptionEntity subscription) {
		SubscriptionEntity subscriptionEntity = getSubscription(
				subscription.getCategoryId(), subscription.getUserId());
		if (subscriptionEntity == null) {
			entityManager.persist(subscription);
			entityManager.flush();
		}
		return subscription;
	}

	private SubscriptionEntity getSubscription(Long categoryId, Long userId) {

		String sqlString = "SELECT s FROM SubscriptionEntity s WHERE categoryId =:categoryIdValue AND userId=:userIdValue";
		TypedQuery<SubscriptionEntity> query = entityManager.createQuery(
				sqlString, SubscriptionEntity.class);
		query.setParameter("categoryIdValue", categoryId);
		query.setParameter("userIdValue", userId);
		return query.getSingleResult();
	}

}
