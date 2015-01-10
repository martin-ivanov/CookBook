package com.cookbook.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.cookbook.persistence.entity.CategoryEntity;
import com.cookbook.persistence.entity.RecipeEntity;

public class CategoryDAOImpl implements CategoryDAO {

	@PersistenceContext(unitName = "cookMateEntityManager")
	private EntityManager entityManager;
	
	@Transactional
	public List<CategoryEntity> getAllCategories() {
		String sqlString = "SELECT u FROM CategoryEntity u";
		TypedQuery<CategoryEntity> query = entityManager.createQuery(sqlString,
				CategoryEntity.class);
		return query.getResultList();
	}

	@Transactional
	public CategoryEntity addCategory(CategoryEntity category) {
		// System.out.println("adding category " + category.getFullName());
		entityManager.persist(category);
		entityManager.flush();
		return category;
	}

	public void updateCategory(CategoryEntity category) {
		entityManager.merge(category);

	}

	@Transactional
	public CategoryEntity getCategoryById(Long id) {
		CategoryEntity category = entityManager.find(CategoryEntity.class, id);
		return category;
	}
	

}
