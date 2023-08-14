package com.example.demo.service.validation;

import com.example.demo.entity.Address;

public class AddressValidationService {
	//	「建物名・部屋番号」以外は必須項目のバリデーション
	public static boolean validateRequiredFields(Address address) {
		return address.getPostNum().equals("") ||
				address.getPrefecture().equals("") ||
				address.getMunicipality().equals("") ||
				address.getHouseNum().equals("");
	}
}
