package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_addresses")
@IdClass(value = AccountAddressKey.class)
public class AccountAddress {
	@Id
	@Column(name = "account_id")
	private Integer accountId; // アカウントID

	@Id
	@Column(name = "address_id")
	private Integer addressId; // あて先ID

	@Column(name = "address_name")
	private String addressName; // あて先名

	@ManyToOne()
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address; // あて先テーブル参照

	public Integer getAddressId() {
		return addressId;
	}

	public String getAddressName() {
		return addressName;
	}

	public Address getAddress() {
		return address;
	}
}
