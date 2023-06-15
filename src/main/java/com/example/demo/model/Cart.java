package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.Item;

@Component
@SessionScope
public class Cart {

	private List<Item> items = new ArrayList<>(); // 商品リスト

	public List<Item> getItems() {
		return items;
	}

	// 合計金額ゲッター
	public int getTotalPrice() {
		return items.stream()
				.mapToInt(item -> item.getSubTotalPrice())
				.sum();
	}
}
