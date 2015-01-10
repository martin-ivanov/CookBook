package com.cookbook.rest.services;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.UserEntity;


/**
 * 
 * @author martin.ivanov
 *
 */
public interface UserService {
	
	/*
	 * ******************** Create related methods **********************
	 * */
	
	/**
	 * Registers a user in the CookBook DB, method needs user details to be set while sending JSON request 
	 * 
	 * @param user
	 * @return the generated id by app
	 * @throws AppException 
	 */
	public UserEntity createUser(UserEntity user) throws AppException;

		
	/*
	 ******************** Read related methods ********************
	  */ 	
		
	/**
	 * Returns a user given its id
	 * 
	 * @param id
	 * @return
	 * @throws AppException 
	 */
	public UserEntity getUserById(Long id) throws AppException;
	
	/**
	 * Returns a user given its credentials
	 * 
	 * @param id
	 * @return
	 * @throws AppException 
	 */

	public UserEntity getUserByCredentials(String username, String password) throws AppException;	
	
		

}