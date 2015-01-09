package com.cookbook.rest.resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.UserEntity;
import com.cookbook.rest.services.UserService;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author martin.ivanov
 * 
 */
@Component
@Path("/users")
public class UserResource {

	@Autowired
	private UserService userService;

	/*
	 * *********************************** CREATE
	 * ***********************************
	 */

	/**
	 * Adds a new user from the given json format (at least email, fullname and
	 * password elements are required at the DB level)
	 * 
	 * @param user
	 * @return
	 * @throws AppException
	 */
	@POST
//	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(@FormParam("username") String userName, @FormParam("email") String email,
			@FormParam("password") String password, @FormParam("gcmToken") String gcmToken) throws AppException {
		UserEntity initialUser = new UserEntity();
		initialUser.setUserName(userName);
		initialUser.setEmail(email);
		initialUser.setPassword(password);
		initialUser.setGcmToken(gcmToken);
		
		UserEntity createUser = userService.createUser(initialUser);
		return Response.status(Response.Status.CREATED)// 201
				.entity(createUser).build();
	}

	/*
	 * *********************************** READ
	 * ***********************************
	 */

	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserByCredentials(@FormParam("username") String username,
			@FormParam("password") String password) throws IOException,
			AppException {
		System.out.println("getById");
		UserEntity user = userService.getUserByCredentials(username, password);
		if (user!=null){
			return Response.status(200).build();
		}
		
		return Response.status(404).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserById(@PathParam("id") Long id) throws IOException,
			AppException {
		System.out.println("getById");
		UserEntity userById = userService.getUserById(id);
		return Response.status(200).entity(userById)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

}