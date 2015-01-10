package com.cookbook.persistence.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name = "category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM CategoryEntity c")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String desc;
	private String name;
	private List<RecipeEntity> recipes;
//	@JsonIgnore
	@JsonBackReference
	private List<UserEntity> users;

	public CategoryEntity() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 4000)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// bi-directional many-to-one association to Recipe
	@OneToMany(mappedBy = "category")
	public List<RecipeEntity> getRecipes() {
		return this.recipes;
	}

	public void setRecipes(List<RecipeEntity> recipes) {
		this.recipes = recipes;
	}

	public RecipeEntity addRecipe(RecipeEntity recipe) {
		getRecipes().add(recipe);
		recipe.setCategory(this);

		return recipe;
	}

	public RecipeEntity removeRecipe(RecipeEntity recipe) {
		getRecipes().remove(recipe);
		recipe.setCategory(null);

		return recipe;
	}

	// bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "categories")
	public List<UserEntity> getUsers() {
		return this.users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

}