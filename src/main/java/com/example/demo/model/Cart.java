package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	// カート内から指定された商品をOptional型で取得
	public Optional<Item> getExistItemOpt(Integer itemId) {
		return items.stream()
				.filter(item -> item.getId() == itemId)
				.findFirst();
	}

	//	指定された商品をカートに追加
	public void add(Item newItem) {
		this.items.add(newItem);
	}
}
