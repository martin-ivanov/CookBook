package com.cookbook.persistence.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM UserEntity u")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String email;
	private String password;
	private String role;
	private String userName;
	private String gcmToken;
	private List<CategoryEntity> categories;

	public UserEntity() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(length=45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Column(length=64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Column(length=45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	@Column(name="user_name", length=100)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	

	@Column(name="gcm_token", length=2000)
	public String getGcmToken() {
		return gcmToken;
	}


	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}


	//bi-directional many-to-many association to Category
	@ManyToMany
	@JoinTable(
		name="subscriptions"
		, joinColumns={
			@JoinColumn(name="user_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="category_id")
			}
		)
	public List<CategoryEntity> getCategories() {
		return this.categories;
	}

	public void setCategories(List<CategoryEntity> categories) {
		this.categories = categories;
	}

}