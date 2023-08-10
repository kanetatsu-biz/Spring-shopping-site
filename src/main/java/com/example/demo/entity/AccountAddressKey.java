package com.example.demo.entity;

import lombok.Data;

// アカウントとあて先の中間テーブルの複合主キー用
@Data
public class AccountAddressKey {
	private Integer accountId; // アカウントID

	private Integer addressId; // あて先ID

	private String addressName; // あて先名
}
