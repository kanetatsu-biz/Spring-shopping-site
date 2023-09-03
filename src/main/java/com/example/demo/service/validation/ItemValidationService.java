package com.example.demo.service.validation;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Item;

public class ItemValidationService {
	//	「カテゴリ―ID」と「説明」、「画像ファイル」以外の必須項目に未入力があるか
	public static boolean validateRequiredFields(Item item) {
		return item.getName().equals("") ||
				item.getPrice() == null ||
				item.getStock() == null;
	}

	//	画像ファイルの拡張子がpng, jpg, jpegのいずれかに当てはまるか
	public static boolean validateFileExtension(MultipartFile imgFile, String imgFileName) {
		return !imgFile.isEmpty() &&
				!(imgFileName.endsWith(".png") ||
						imgFileName.endsWith(".jpg") ||
						imgFileName.endsWith(".jpeg"));
	}
}
