package com.cookbook.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the subscriptions database table.
 * 
 */
@Entity
@Table(name="subscriptions")
@NamedQuery(name="Subscription.findAll", query="SELECT s FROM SubscriptionEntity s")
public class SubscriptionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long categoryId;
	private Long id;
	private Long userId;

	public SubscriptionEntity() {
	}


	@Column(name="category_id")
	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Column(name="user_id")
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}