package com.cookbook.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.cookbook.persistence.entity.UserEntity;

public class UserDAOImpl implements UserDAO {

	@PersistenceContext(unitName = "cookMateEntityManager")
	private EntityManager entityManager;

	public List<UserEntity> getAllUsers() {
		String sqlString = "SELECT u FROM UserEntity u";
		TypedQuery<UserEntity> query = entityManager.createQuery(sqlString,
				UserEntity.class);
		return query.getResultList();
	}

	@Transactional
	public UserEntity addUser(UserEntity user) {
		System.out.println("in addUser method in DAO");
		entityManager.persist(user);
		entityManager.flush();
		return user;
	}

	public void updateUser(UserEntity user) {
		entityManager.merge(user);

	}

	public UserEntity getUserById(Long id) {
		UserEntity user = entityManager.find(UserEntity.class, id);
		System.out.println("id:" + user.getId());
		return user;
	}

	public UserEntity getUserByToken(String token) {
		UserEntity user = (UserEntity) entityManager
				.createQuery(
						"SELECT u FROM UserEntity u where u.token = :tokenValue")
				.setParameter("tokenValue", token).getSingleResult();
		return user;
	}

	@Override
	public UserEntity getUserByCredentials(String username, String password) {
		UserEntity user = (UserEntity) entityManager
				.createQuery(
						"SELECT u FROM UserEntity u where u.user_name = :userNameValue and u.password=:passwordValue")
				.setParameter("userNameValue", username)
				.setParameter("passwordValue", password).getSingleResult();
		return user;
	}

}
