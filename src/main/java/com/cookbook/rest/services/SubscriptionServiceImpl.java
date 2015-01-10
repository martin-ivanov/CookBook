package com.cookbook.rest.services;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.cookbook.errors.AppException;
import com.cookbook.jms.JMSMessageProducer;
import com.cookbook.persistence.entity.SubscriptionEntity;

public class SubscriptionServiceImpl implements SubscriptionService {

	public static final String SUBSCRIPTION_QUEUE = "COOKBOOK.CATEGORY";

	public static final String SUBSCRIBE_OPERATION = "subscribe";
	public static final String UNSUBSCRIBE_OPERATION = "unsubscribe";

	@Autowired
	JMSMessageProducer producer;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public SubscriptionEntity unsubcribe(Long userId, Long categoryId)
			throws AppException {
		String response = "";
		SubscriptionEntity request = new SubscriptionEntity();
		request.setUserId(userId);
		request.setCategoryId(categoryId);
		try {
			response = producer.request(
					objectMapper.writeValueAsString(request), SUBSCRIPTION_QUEUE,
					UNSUBSCRIBE_OPERATION, "");
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SubscriptionEntity responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response,
					SubscriptionEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseEntity;
	}

	@Override
	public SubscriptionEntity subcribe(Long userId, Long categoryId)
			throws AppException {
		String response = "";
		SubscriptionEntity request = new SubscriptionEntity();
		request.setUserId(userId);
		request.setCategoryId(categoryId);
		try {
			response = producer.request(
					objectMapper.writeValueAsString(request), SUBSCRIPTION_QUEUE,
					SUBSCRIBE_OPERATION, "");
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SubscriptionEntity responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response,
					SubscriptionEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseEntity;
	}

	public JMSMessageProducer getProducer() {
		return producer;
	}

	public void setProducer(JMSMessageProducer producer) {
		this.producer = producer;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}





}
