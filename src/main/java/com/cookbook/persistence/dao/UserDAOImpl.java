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
		List<UserEntity> users = entityManager
				.createQuery(
						"SELECT u FROM UserEntity u where u.userName = :userNameValue and u.password=:passwordValue", UserEntity.class)
				.setParameter("userNameValue", username)
				.setParameter("passwordValue", password)
				.getResultList();
		if (!users.isEmpty()){
			return users.get(0);
		}
		return null;
	}

}
