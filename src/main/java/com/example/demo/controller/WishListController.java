package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.WishList;
import com.example.demo.model.LoginUser;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.WishListRepository;

@Controller
public class WishListController {

	@Autowired
	WishListRepository wishListRepository;

	@Autowired
	LoginUser loginUser;

	@Autowired
	ItemRepository itemRepository;

	// ほしい物リスト一覧表示
	@GetMapping("/wishList")
	public String index(
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			Model model) {

		//ユーザーIDに紐づくほしい物リストを取得
		List<WishList> userWishItems = wishListRepository.findByCustomerId(loginUser.getId());

		//取得したデータとエラーメッセージを画面に渡す
		model.addAttribute("wishList", userWishItems);
		model.addAttribute("errMes", errMes);

		return "wishList";
	}

	//　ほしい物リスト追加
	@PostMapping("/wishList/add")
	public String add(
			RedirectAttributes redirectAttributes,
			@RequestParam("itemId") Integer itemId) {

		String errMes = ""; // エラーメッセージ

		//すでにリストに追加されている場合のバリデーション
		if (wishListRepository.existsByCustomerIdAndItemId(loginUser.getId(), itemId)) {
			errMes = "この商品は既に追加済みです。";
		} else {
			// 必要な情報をテーブルに追加
			wishListRepository
					.save(new WishList(loginUser.getId(), itemId));
		}

		// エラーメッセージをリダイレクト先に送る
		redirectAttributes.addAttribute("errMes", errMes);
		return "redirect:/wishList";
	}

	//	ほしい物リスト削除
	@PostMapping("/wishList/delete")
	public String delete(
			@RequestParam("itemId") Integer itemId) {

		//	指定された商品IDをもとにカートから削除
		wishListRepository.deleteByCustomerIdAndItemId(loginUser.getId(), itemId);

		// カート一覧画面にリダイレクト
		return "redirect:/wishList";
	}

}
