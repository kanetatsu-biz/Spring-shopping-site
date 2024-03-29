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

	@Column(name = "category_id", nullable = false)
	private Integer categoryId; // カテゴリーID

	@Column(unique = true, nullable = false)
	private String name; // 商品名

	@Column(nullable = false)
	private Integer price; // 価格

	private String description; // 説明

	@Column(name = "file_name")
	private String fileName = ""; // 画像ファイル名

	@Transient
	private Integer quantity; // 数量（カートの処理で使用）

	@Column(nullable = false)
	private Integer stock; // 在庫数（カートの処理で使用）

	@Transient
	private Integer subTotalPrice; // 小計（カートの処理で使用）

	@Column(name = "is_deleted")
	private boolean isDeleted = false; // ソフトデリート用のフラグ

	@Transient
	private boolean isInWishList = false; // wishListにあるか

	public Item() {
	};

	//	バリデーション用のコンストラクタ
	public Item(Integer id, Integer categoryId, String name, Integer price, String description, String fileName,
			Integer stock) {
		this.id = id;
		this.categoryId = categoryId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.fileName = fileName;
		this.stock = stock;
	}

	public Integer getId() {
		return id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	//	小計　＝　価格　＊　数量
	public Integer getSubTotalPrice() {
		return price * quantity;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isInWishList() {
		return isInWishList;
	}

	public void setInWishList(boolean isInWishList) {
		this.isInWishList = isInWishList;
	}
}
