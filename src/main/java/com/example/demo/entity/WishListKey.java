package com.example.demo.entity;

import lombok.Data;

@Data
public class WishListKey {
	private Integer customerId; // アカウントID

	private Integer itemId; // あて先ID
}
