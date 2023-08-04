package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 注文ID

	@Column(name = "customer_id")
	private Integer customerId; // 顧客ID

	@Column(name = "ordered_datetime")
	private LocalDateTime orderedDatetime; // 注文日時

	@Column(name = "total_price")
	private Integer totalPrice; // 合計金額

	@Column(name = "address_id")
	private Integer addressId; // あて先ID

	// コンストラクタ
	public Order() {
		super();
	}

	public Order(Integer customerId, LocalDateTime orderedDatetime, Integer totalPrice, Integer addressId) {
		this.customerId = customerId;
		this.orderedDatetime = orderedDatetime;
		this.totalPrice = totalPrice;
		this.addressId = addressId;
	}

	// ゲッター
	public Integer getId() {
		return id;
	}
}
