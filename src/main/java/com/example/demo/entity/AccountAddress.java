package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_addresses")
public class AccountAddress {
	@Id
	@Column(name = "account_id")
	private Integer accountId; // アカウントID

	@Id
	@Column(name = "address_id")
	private Integer addressId; // あて先ID

	@Column(name = "address_name")
	private String addressName; // あて先名
}
