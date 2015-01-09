package com.cookbook.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the recipe database table.
 * 
 */
@Entity
@Table(name="recipe")
@NamedQuery(name="Recipe.findAll", query="SELECT r FROM RecipeEntity r")
public class RecipeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String desc;
	private String name;
	private CategoryEntity category;

	public RecipeEntity() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	@Column(length=4000)
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	@Column(length=100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to Category
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id", nullable=false)
	public CategoryEntity getCategory() {
		return this.category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

}