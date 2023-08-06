package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 注文履歴詳細表示用ビュー
@Entity
@Table(name = "v_order_history_details")
public class VOrderHistoryDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 注文詳細ID

	@Column(name = "order_id")
	private Integer orderId; // 注文ID

	@Column(name = "item_name")
	private String itemName; // 商品名

	@Column(name = "item_price")
	private Integer itemPrice; // 商品価格

	@Column(name = "file_name")
	private String fileName; // 画像ファイル名

	private Integer quantity; // 数量

	public String getItemName() {
		return itemName;
	}

	public Integer getItemPrice() {
		return itemPrice;
	}

	public String getFileName() {
		return fileName;
	}

	public Integer getQuantity() {
		return quantity;
	}
}
