package com.cookbook.rest.services;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.cookbook.errors.AppException;
import com.cookbook.jms.JMSMessageProducer;
import com.cookbook.persistence.entity.CategoryEntity;
import com.cookbook.rest.response.wrapper.CategoryWrapper;

public class CategoryServiceImpl implements CategoryService {

	public static final String CATEGORY_QUEUE = "COOKBOOK.CATEGORY";

	public static final String RETRIEVE_CATEGORY_OPERATION = "getCategory";

	@Autowired
	JMSMessageProducer producer;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public CategoryEntity createCategory(CategoryEntity category)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryEntity getCategoryById(Long id) throws AppException {
		String response = "";
		CategoryEntity request = new CategoryEntity();
		request.setId(id);
		try {
			response = producer.request(
					objectMapper.writeValueAsString(request), CATEGORY_QUEUE,
					RETRIEVE_CATEGORY_OPERATION, "");
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		CategoryEntity responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response,
					CategoryEntity.class);
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

	@Override
	public CategoryWrapper getCategories() throws AppException {
		String response = "";
		response = producer.request("getAll", CATEGORY_QUEUE,
				RETRIEVE_CATEGORY_OPERATION, "");
		System.out.println(response);

		CategoryWrapper responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response,
					CategoryWrapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseEntity;
	}

}
