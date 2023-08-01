package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	Cart cart;

	// 商品一覧表示
	@GetMapping("/items")
	public String index(
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			Model model) {
		//		全カテゴリーを取得
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		List<Item> items = null;

		if (categoryId == null) {
			//		全商品を取得
			items = itemRepository.findAll();
		} else {
			//		カテゴリーで絞った商品を取得
			items = itemRepository.findByCategoryId(categoryId);
		}
		//すでにカートに入っている商品は在庫を変更
		for (Item cartItem : cart.getItems()) {
			for (Item item : items) {
				//	カートに既に商品が存在している場合
				if (item.getId() == cartItem.getId()) {
					//	在庫情報をもとに購入可能な数量を計算しなおす
					item.setStock(item.getStock() - cartItem.getQuantity());
					break;
				}
			}
		}

		model.addAttribute("items", items);

		return "items";
	}

	// 商品詳細表示
	@GetMapping("/items/{itemId}")
	public String show(
			@PathVariable("itemId") Integer itemId,
			Model model) {

		//	商品IDをもとに商品を取得
		Item item = itemRepository.findById(itemId).get();
		model.addAttribute("item", item);

		return "showItem";
	}
}
