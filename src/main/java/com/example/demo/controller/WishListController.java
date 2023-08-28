package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.entity.WishList;
import com.example.demo.model.LoginUser;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.WishListRepository;

@Controller
public class WishListController {

	@Autowired
	WishList wishList;

	@Autowired
	WishListRepository wishListRepository;

	@Autowired
	LoginUser loginUser;

	@Autowired
	ItemRepository itemRepository;

	// ほしい物リスト一覧表示
	@GetMapping("/wishList")
	public String index(
			Model model) {

		//ほしい物リストを取得
		List<WishList> userWishItems = wishListRepository.findByCustomerId(loginUser.getId());

		//取得したデータを画面に渡す
		model.addAttribute("wishList", userWishItems);

		return "wishList";
	}

	//　ほしい物リスト追加
	@PostMapping("/wishList/add")
	public String add(
			@RequestParam("itemId") int itemId) {

		//　対象の商品を取得
		Item item = itemRepository.findById(itemId).get();

		//　必要な情報をテーブルに追加
		WishList newWishItem = new WishList();
		newWishItem.setCustomerId(loginUser.getId());
		newWishItem.setItemId(itemId);
		newWishItem.setName(item.getName());
		newWishItem.setFileName(item.getFileName());
		wishListRepository.save(newWishItem);

		return "redirect:/wishList";
	}

	//	ほしい物リスト削除
	@PostMapping("/wishList/delete")
	public String delete(
			@RequestParam("id") int wishListId) {

		//	指定された商品IDをもとにカートから削除
		wishListRepository.deleteById(wishListId);

		// カート一覧画面にリダイレクト
		return "redirect:/wishList";
	}

}
