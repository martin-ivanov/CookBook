package com.cookbook.persistence.dao;

import java.util.List;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.CategoryEntity;

public interface CategoryDAO {
	
	/**
	 * Returns a list of category details currently registered
	 * 
	 * @param 
	 * @return a list of category entities
	 * @throws AppException 
	 */
	public abstract List<CategoryEntity> getAllCategories();
	
	/**
	 * Persists new category entity in to the database
	 * 
	 * @param CategoryEntity instance
	 * @return 
	 * @throws AppException 
	 */
	public abstract CategoryEntity addCategory (CategoryEntity category);
	
	/**
	 * Updates category entity in the database
	 * 
	 * @param CategoryEntity instance
	 * @return 
	 * @throws AppException 
	 */
	public abstract void updateCategory (CategoryEntity category);
	
	/**
	 * Retrieves category entity from the database by a given ID
	 * 
	 * @param id
	 * @return CategoryDetail instance
	 * @throws AppException 
	 */
	public CategoryEntity getCategoryById(Long id);

}
