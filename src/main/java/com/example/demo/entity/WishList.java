package com.example.demo.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Component
@Entity
@Table(name = "wishes")
@IdClass(value = WishListKey.class)
public class WishList {

	//　itemテーブルをitemのidとitem_idを元にjoin
	@ManyToOne
	@JoinColumn(name = "item_id", insertable = false, updatable = false)
	private Item item;

	@Id
	@Column(name = "customer_id")
	private Integer customerId;

	@Id
	@Column(name = "item_id")
	private Integer itemId;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	//データを詰める
	public WishList() {
	}

	public WishList(Integer customer_id, Integer item_id) {
		this.customerId = customer_id;
		this.itemId = item_id;
	}

	public Item getItem() {
		return item;
	}

}
