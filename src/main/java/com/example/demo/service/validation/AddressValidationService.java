package com.example.demo.service.validation;

import com.example.demo.entity.Address;

public class AddressValidationService {
	//	「建物名・部屋番号」以外の必須項目に未入力があるか
	public static boolean validateRequiredFields(Address address) {
		return address.getPostNum().equals("") ||
				address.getPrefecture().equals("") ||
				address.getMunicipality().equals("") ||
				address.getHouseNum().equals("");
	}
}
