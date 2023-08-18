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
import com.example.demo.service.SearchService;

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
			@RequestParam(value = "itemName", defaultValue = "") String itemName,
			@RequestParam(value = "matchPattern", defaultValue = "partial") String matchPattern,
			@RequestParam(value = "minPrice", defaultValue = "") Integer minPrice,
			@RequestParam(value = "maxPrice", defaultValue = "") Integer maxPrice,
			Model model) {

		//	全カテゴリーを取得
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		List<Item> items = null;

		//	カテゴリーが指定されていたら他の検索条件は無視
		if (categoryId != null) {
			//	カテゴリーで絞った商品を取得
			items = itemRepository.findByCategoryId(categoryId);
		} else {
			//	入力値を文字列検索用に置き換え
			String itemNameCriteria = SearchService.getValueWithWildcard(itemName, matchPattern);

			//	指定された値をもとに検索した商品を取得
			items = itemRepository.searchByCriteria(
					itemNameCriteria, minPrice, maxPrice);
		}

		//すでにカートに入っている商品は在庫を変更
		for (Item cartItem : cart.getItems()) {
			for (Item item : items) {
				calcPurchasableStock(item, cartItem);
			}
		}

		model.addAttribute("items", items);

		//	商品名検索のマッチパターンを取得し、画面に渡す
		model.addAttribute("matchPatterns", SearchService.stringMatchPatterns);

		//	検索条件を保持
		model.addAttribute("itemName", itemName);
		model.addAttribute("matchPattern", matchPattern);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);

		return "items";
	}

	// 商品詳細表示
	@GetMapping("/items/{itemId}")
	public String show(
			@PathVariable("itemId") Integer itemId,
			Model model) {

		//	商品IDをもとに商品を取得
		Item item = itemRepository.findById(itemId).get();

		//すでにカートに入っている商品は在庫を変更
		for (Item cartItem : cart.getItems()) {
			calcPurchasableStock(item, cartItem);
		}

		model.addAttribute("item", item);

		return "showItem";
	}

	public void calcPurchasableStock(Item item, Item cartItem) {

		//	カートに既に商品が存在している場合
		if (item.getId() == cartItem.getId()) {
			//	在庫情報をもとに購入可能な数量を計算しなおす
			item.setStock(item.getStock() - cartItem.getQuantity());
		}

	}
}
