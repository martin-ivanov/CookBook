package com.cookbook.rest.services;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.cookbook.errors.AppException;
import com.cookbook.jms.JMSMessageProducer;
import com.cookbook.persistence.entity.UserEntity;

public class UserServiceImpl implements UserService {

	public static final String USER_QUEUE = "COOKBOOK.USER";
	public static final String ADD_USER_OPERATION = "addUser";
	public static final String REMOVE_USER_OPERATION = "removeUser";
	public static final String RETRIEVE_USER_OPERATION = "getUser";
	
	
	@Autowired
	JMSMessageProducer producer;
	
    @Autowired
    ObjectMapper objectMapper;
    


	public void updateFullyUser(UserEntity podcast) throws AppException {
		// TODO Auto-generated method stub

	}

	public void updatePartiallyUser(UserEntity podcast) throws AppException {
		// TODO Auto-generated method stub

	}

	public UserEntity verifyUserExistenceById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity createUser(UserEntity user) throws AppException {
		System.out.println("send message");
		String response = "";
		try {
			response = producer.request(objectMapper.writeValueAsString(user), USER_QUEUE, ADD_USER_OPERATION);
			System.out.println(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserEntity responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response, UserEntity.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseEntity;
	}

	@Override
	public UserEntity getUserById(Long id) throws AppException {
		String response = "";
		UserEntity request = new UserEntity();
		request.setId(id);
		try {
			response = producer.request(objectMapper.writeValueAsString(request), USER_QUEUE, RETRIEVE_USER_OPERATION);
			System.out.println(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserEntity responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response, UserEntity.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseEntity;
	}


	@Override
	public UserEntity getUserByCredentials(String userName, String password) {
		String response = "";
		UserEntity request = new UserEntity();
		request.setUserName(userName);
		request.setPassword(password);
		
		try {
			response = producer.request(objectMapper.writeValueAsString(request), USER_QUEUE, RETRIEVE_USER_OPERATION);
			System.out.println(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserEntity responseEntity = null;
		try {
			responseEntity = objectMapper.readValue(response, UserEntity.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return responseEntity;
	}
	

	
	
	

}
