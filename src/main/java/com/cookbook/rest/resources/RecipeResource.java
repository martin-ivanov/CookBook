package com.cookbook.rest.resources;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cookbook.errors.AppException;
import com.cookbook.persistence.entity.CategoryEntity;
import com.cookbook.persistence.entity.RecipeEntity;
import com.cookbook.rest.response.wrapper.RecipeWrapper;
import com.cookbook.rest.services.CategoryService;
import com.cookbook.rest.services.RecipeService;

/**
 * 
 * Service class that handles REST requests
 * 
 * @author martin.ivanov
 * 
 */
@Component
@Path("/recipes")
public class RecipeResource {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private CategoryService categoryService;

	/*
	 * *********************************** CREATE
	 * ***********************************
	 */

	/**
	 * Adds a new recipe from the given json format (at least email, fullname
	 * and password elements are required at the DB level)
	 * 
	 * @param recipe
	 * @return
	 * @throws AppException
	 */
	@POST
	// @Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createRecipe(@FormParam("name") String name, @FormParam("desc") String desc, @FormParam("categoryId") Long categoryId) throws AppException {
		RecipeEntity recipe = new RecipeEntity();
		recipe.setName(name);
		recipe.setDesc(desc);
		CategoryEntity category = categoryService.getCategoryById(categoryId);
		if (category != null) {
			recipe.setCategory(category);
		}

		RecipeEntity createRecipe = recipeService.createRecipe(recipe);
		return Response.status(Response.Status.CREATED)// 201
				.entity(createRecipe).build();
	}

	/*
	 * *********************************** READ
	 * ***********************************
	 */

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRecipeById(@PathParam("id") Long id) throws IOException,
			AppException {
		System.out.println("getById");
		RecipeEntity recipeById = recipeService.getRecipeById(id);
		return Response.status(200).entity(recipeById)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRecipeByName(@QueryParam("name") String name)
			throws IOException, AppException {
		System.out.println("getByName recipe");
		RecipeWrapper recipesByName = recipeService.searchRecipesByName(name);
		return Response.status(200).entity(recipesByName)
				.header("Access-Control-Allow-Headers", "X-extra-header")
				.allow("OPTIONS").build();
	}

	public void setRecipeService(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	public RecipeService getRecipeService() {
		return recipeService;
	}

}