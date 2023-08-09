package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;

@Controller
public class ItemController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ItemRepository itemRepository;

	// 商品一覧表示
	@GetMapping("/items")
	public String index(
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "itemName", defaultValue = "") String itemName,
			@RequestParam(value = "matchCondition", defaultValue = "partial") String matchCondition,
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
			//	文字列検索条件
			String itemNameCriteria = itemName;

			//	未入力の場合はスルー
			if (!itemNameCriteria.equals("")) {
				switch (matchCondition) {
				//	完全一致
				case "all":
					break;
				//	部分一致
				case "partial":
					itemNameCriteria = '%' + itemNameCriteria + '%';
					break;
				//	前方一致
				case "starting":
					itemNameCriteria = itemNameCriteria + '%';
					break;
				//	後方一致
				case "ending":
					itemNameCriteria = '%' + itemName;
					break;
				}
			}

			//	指定された値をもとに検索した商品を取得
			items = itemRepository.searchByCriteria(
					itemNameCriteria, minPrice, maxPrice);
		}

		model.addAttribute("items", items);

		//	商品名検索の条件をマップに格納し、画面に渡す
		Map<String, String> matchConditions = new HashMap<String, String>() {
			{
				put("partial", "部分一致");
				put("all", "完全一致");
				put("starting", "前方一致");
				put("ending", "後方一致");
			}
		};
		model.addAttribute("matchConditions", matchConditions);

		//	検索条件を保持
		model.addAttribute("itemName", itemName);
		model.addAttribute("matchCondition", matchCondition);
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
		model.addAttribute("item", item);

		return "showItem";
	}
}
