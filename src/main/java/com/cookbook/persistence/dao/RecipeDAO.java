package com.cookbook.persistence.dao;

import java.util.List;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.RecipeEntity;

public interface RecipeDAO {
	
	/**
	 * Returns a list of recipe details currently registered
	 * 
	 * @param 
	 * @return a list of recipe entities
	 * @throws AppException 
	 */
	public abstract List<RecipeEntity> getAllRecipes();
	
	/**
	 * Persists new recipe entity in to the database
	 * 
	 * @param RecipeEntity instance
	 * @return 
	 * @throws AppException 
	 */
	public abstract RecipeEntity addRecipe (RecipeEntity recipe);
	
	/**
	 * Updates recipe entity in the database
	 * 
	 * @param RecipeEntity instance
	 * @return 
	 * @throws AppException 
	 */
	public abstract void updateRecipe (RecipeEntity recipe);
	
	/**
	 * Retrieves recipe entity from the database by a given ID
	 * 
	 * @param id
	 * @return RecipeDetail instance
	 * @throws AppException 
	 */
	public RecipeEntity getRecipeById(Long id);

}
