package com.cookbook.rest.services;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.CategoryEntity;
import com.cookbook.rest.response.wrapper.CategoryWrapper;


/**
 * 
 * @author martin.ivanov
 *
 */
public interface CategoryService {
	
	/*
	 * ******************** Create related methods **********************
	 * */
	
	/**
	 * Registers a category in the Outlink DB, method needs category details to be set while sending JSON request 
	 * 
	 * @param category
	 * @return the generated id by app
	 * @throws AppException 
	 */
	public CategoryEntity createCategory(CategoryEntity category) throws AppException;

		
	/*
	 ******************** Read related methods ********************
	  */ 	
		
	/**
	 * Returns a category given its id
	 * 
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	public CategoryEntity getCategoryById(Long id) throws AppException;	
	
	/**
	 * Returns all categories as list
	 * 
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	public CategoryWrapper getCategories() throws AppException;
	

}