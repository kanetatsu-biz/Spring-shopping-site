package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 注文履歴表示用ビュー
@Entity
@Table(name = "v_order_histories")
public class VOrderHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 注文ID

	@Column(name = "acoount_name")
	private String acoountName; // アカウント名

	@Column(name = "ordered_datetime")
	private String orderedDatetime; // 注文日時

	@Column(name = "total_price")
	private Integer totalPrice; // 合計金額

	public Integer getId() {
		return id;
	}

	public String getAcoountName() {
		return acoountName;
	}

	public String getOrderedDatetime() {
		return orderedDatetime;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}
}
