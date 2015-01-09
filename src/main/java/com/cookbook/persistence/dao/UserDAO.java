package com.cookbook.persistence.dao;

import java.util.List;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.UserEntity;

public interface UserDAO {
	
	/**
	 * Returns a list of user details currently registered
	 * 
	 * @param 
	 * @return a list of user entities
	 * @throws AppException 
	 */
	public abstract List<UserEntity> getAllUsers();
	
	/**
	 * Persists new user entity in to the database
	 * 
	 * @param UserEntity instance
	 * @return 
	 * @throws AppException 
	 */
	public abstract UserEntity addUser (UserEntity user);
	
	/**
	 * Updates user entity in the database
	 * 
	 * @param UserEntity instance
	 * @return 
	 * @throws AppException 
	 */
	public abstract void updateUser (UserEntity user);
	
	/**
	 * Retrieves user entity from the database by a given ID
	 * 
	 * @param id
	 * @return UserDetail instance
	 * @throws AppException 
	 */
	public UserEntity getUserById(Long id);

	/**
	 * Retrieves user entity from the database by a given credentials
	 * 
	 * @param id
	 * @return UserDetail instance
	 * @throws AppException 
	 */
	public UserEntity getUserByCredentials(String username, String password);
}
