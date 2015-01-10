package com.cookbook.rest.services;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.cookbook.errors.AppException;
import com.cookbook.jms.JMSMessageProducer;
import com.cookbook.persistence.entity.RecipeEntity;
import com.cookbook.rest.response.wrapper.RecipeWrapper;


/**
 * 
 * @author martin.ivanov
 *
 */
public class RecipeServiceImpl implements RecipeService {
	
	private static final String RETRIEVE_RECIPE_OPERATION = "getRecipe";

	private static final String RECIPIES_QUEUE = "COOKBOOK.RECIPE";

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	JMSMessageProducer producer;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public RecipeEntity createRecipe(RecipeEntity recipe) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RecipeEntity getRecipeById(Long id) throws AppException {
		
		String response = "";
		RecipeEntity request = new RecipeEntity();
		request.setId(id);
		try {
			response = producer.request(
					objectMapper.writeValueAsString(request), RECIPIES_QUEUE,
					RETRIEVE_RECIPE_OPERATION);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		RecipeEntity responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response,
					RecipeEntity.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseEntity;
	}
	
	
	@Override
	public RecipeWrapper searchRecipesByName(String searchForName) throws AppException {
		String response = "";
		RecipeEntity request = new RecipeEntity();
		request.setName(searchForName);
		try {
			response = producer.request(
					objectMapper.writeValueAsString(request), RECIPIES_QUEUE,
					RETRIEVE_RECIPE_OPERATION);
			System.out.println(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		RecipeWrapper responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response,
					RecipeWrapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responseEntity;
	}


	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
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