package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
			@RequestParam(value = "min", defaultValue = "") Integer min,
			@RequestParam(value = "max", defaultValue = "") Integer max,
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
			ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase(); // 大文字小文字区別なし
			switch (matchCondition) {
			//	部分一致
			case "partial":
				matcher = matcher.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
				break;
			//	完全一致
			case "all":
				matcher = matcher.withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

				break;
			//	前方一致
			case "starting":
				matcher = matcher.withStringMatcher(ExampleMatcher.StringMatcher.STARTING);
				break;
			//	後方一致
			case "ending":
				matcher = matcher.withStringMatcher(ExampleMatcher.StringMatcher.ENDING);
				break;
			}

			//	検索条件をもとにQuery by Example用のオブジェクトの作成
			Item itemSearchCondition = new Item();
			if (!itemName.equals("")) {
				//	商品名が入力されていれば条件に追加
				itemSearchCondition.setName(itemName);
			}

			//	Query by Exampleの実行
			Example<Item> example = Example.of(itemSearchCondition, matcher);
			items = itemRepository.findAll(example);
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
