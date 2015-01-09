package com.cookbook.rest.services;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.RecipeEntity;


/**
 * 
 * @author martin.ivanov
 *
 */
public interface RecipeService {
	
	/*
	 * ******************** Create related methods **********************
	 * */
	
	/**
	 * Registers a recipe in the Outlink DB, method needs recipe details to be set while sending JSON request 
	 * 
	 * @param recipe
	 * @return the generated id by app
	 * @throws AppException 
	 */
	public RecipeEntity createRecipe(RecipeEntity recipe) throws AppException;

		
	/*
	 ******************** Read related methods ********************
	  */ 	
		
	/**
	 * Returns a recipe given its id
	 * 
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	public RecipeEntity getRecipeById(Long id) throws AppException;
		

}