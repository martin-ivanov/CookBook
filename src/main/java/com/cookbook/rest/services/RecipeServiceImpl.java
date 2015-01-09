package com.cookbook.rest.services;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.cookbook.errors.AppException;
import com.cookbook.jms.JMSMessageProducer;
import com.cookbook.persistence.entity.RecipeEntity;


/**
 * 
 * @author martin.ivanov
 *
 */
public class RecipeServiceImpl implements RecipeService {
	
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
		// TODO Auto-generated method stub
		return null;
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