package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class FormatService {
	//	渡された価格を「\1,000」のようにフォーマットして返す
	public static String getFormattedPrice(Integer targetPrice) {
		return "&yen" + String.format("%,d", targetPrice);
	}

	//	渡された日時を「年-月-日 時:分」のようにフォーマットして返す
	public static String getFormattedDateTime(LocalDateTime dateTime) {
		// フォーマットパターンを指定
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		return dateTime.format(formatter);
	}
}
