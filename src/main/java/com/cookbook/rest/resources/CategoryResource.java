package com.cookbook.rest.resources;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.CategoryEntity;
import com.cookbook.persistence.entity.SubscriptionEntity;
import com.cookbook.rest.response.wrapper.CategoryWrapper;
import com.cookbook.rest.response.wrapper.RecipeWrapper;
import com.cookbook.rest.services.CategoryService;
import com.cookbook.rest.services.SubscriptionService;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author martin.ivanov
 * 
 */
@Component
@Path("/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SubscriptionService subscriptionService;

	/*
	 * *********************************** CREATE
	 * ***********************************
	 */

	/**
	 * Adds a new category from the given json format (at least email, fullname
	 * and password elements are required at the DB level)
	 * 
	 * @param category
	 * @return
	 * @throws AppException
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createCategory(@FormParam("name") String categoryName,
			@FormParam("desc") String categoryDesc) throws AppException {
		CategoryEntity category = new CategoryEntity();
		category.setName(categoryName);
		category.setDesc(categoryDesc);
		CategoryEntity createCategory = categoryService
				.createCategory(category);
		if (createCategory != null) {
			return Response.status(Response.Status.CREATED)// 201
					.entity(createCategory).build();
		}

		return Response.status(Response.Status.BAD_REQUEST).build();

	}

	@POST
	@Path("/{id}/subscribe")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response subcribeForCategory(@PathParam("id") Long id,
			@FormParam("userId") Long userId) throws IOException, AppException {
		System.out.println("subscribe");
		SubscriptionEntity subscription = subscriptionService.subcribe(userId,
				id);
		if (subscription != null) {
			return Response.status(200).entity(subscription)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@POST
	@Path("/{id}/unsubscribe")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response unsubcribeForCategory(@PathParam("id") Long id,
			@FormParam("userId") Long userId) throws IOException, AppException {
		System.out.println("subscribe");
		SubscriptionEntity subscription = subscriptionService.unsubcribe(
				userId, id);

		return Response.status(200).entity(subscription)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	/*
	 * *********************************** READ
	 * ***********************************
	 */

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCategoryById(@PathParam("id") Long id)
			throws IOException, AppException {
		System.out.println("getById");
		CategoryEntity categoryById = categoryService.getCategoryById(id);
		if (categoryById != null) {
			return Response.status(200).entity(categoryById)
					.header("Access-Control-Allow-Headers", "X-extra-header")
					.allow("OPTIONS").build();
		}

		return Response.status(Response.Status.BAD_REQUEST).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCategories() throws IOException, AppException {
		System.out.println("getAll");
		CategoryWrapper categories = categoryService.getCategories();
		return Response.status(200).entity(categories)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
		
	}

	@GET
	@Path("/{id}/recipes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getCategoryRecipes(@PathParam("id") Long id)
			throws IOException, AppException {
		System.out.println("getAllRecipes");
		CategoryEntity category = categoryService.getCategoryById(id);
		RecipeWrapper rw = new RecipeWrapper();
		if (category != null) {
			rw.setList(category.getRecipes());
		}
		return Response.status(200).entity(rw)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

}