package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 商品ID

	@Column(name = "category_id")
	private Integer categoryId; // カテゴリーID

	private String name; // 商品名

	private Integer price; // 価格

	private String description; // 説明

	@Transient
	private Integer quantity; // 数量（カートの処理で使用）

	@Transient
	private Integer subTotalPrice; // 小計（カートの処理で使用）

	public Integer getId() {
		return id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	//	小計　＝　価格　＊　数量
	public Integer getSubTotalPrice() {
		return price * quantity;
	}
}
