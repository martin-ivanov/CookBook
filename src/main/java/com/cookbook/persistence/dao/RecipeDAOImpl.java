package com.cookbook.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cookbook.persistence.entity.CategoryEntity;
import com.cookbook.persistence.entity.RecipeEntity;
import com.cookbook.persistence.entity.UserEntity;
import com.cookbook.util.GcmHelper;

public class RecipeDAOImpl implements RecipeDAO {

	@PersistenceContext(unitName = "cookMateEntityManager")
	private EntityManager entityManager;

	@Autowired
	CategoryDAO categoryDao;

	public List<RecipeEntity> getAllRecipes() {
		String sqlString = "SELECT u FROM RecipeEntity u";
		TypedQuery<RecipeEntity> query = entityManager.createQuery(sqlString,
				RecipeEntity.class);
		return query.getResultList();
	}

	@Transactional
	public RecipeEntity addRecipe(RecipeEntity recipe) {
		entityManager.persist(recipe);
		entityManager.flush();

		CategoryEntity category = categoryDao.getCategoryById(recipe.getCategory().getId());
		if (category != null) {
			for (UserEntity user : category.getUsers()) {
				GcmHelper.sendNotification(user.getGcmToken(), "", "New recipe: " + recipe.getName(), recipe.getDesc());
			}
		}
		return recipe;
	}

	public void updateRecipe(RecipeEntity recipe) {
		entityManager.merge(recipe);

	}

	public RecipeEntity getRecipeById(Long id) {
		RecipeEntity recipe = entityManager.find(RecipeEntity.class, id);
		System.out.println("id:" + recipe.getId());
		return recipe;
	}
	
	
	public List<RecipeEntity> searchRecipesByName() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<RecipeEntity> query = cb.createQuery(RecipeEntity.class);
		Root<RecipeEntity> root = query.from(RecipeEntity.class);
		Expression<Long> categoryId = root.get("categoryId");
		query.groupBy(categoryId);
		return entityManager.createQuery(query).getResultList();
	}

}
