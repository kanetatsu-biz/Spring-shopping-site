package com.example.demo.entity;

import lombok.Data;

@Data
public class WishListKey {
	private Integer customerId; // ユーザーID

	private Integer itemId; // 商品ID
}
