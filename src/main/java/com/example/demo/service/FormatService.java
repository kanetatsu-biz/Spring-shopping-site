package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class FormatService {
	//	渡された価格を「\1,000」のようにフォーマットして返す
	public static String getFormattedPrice(Integer targetPrice) {
		return "&yen" + String.format("%,d", targetPrice);
	}
}
