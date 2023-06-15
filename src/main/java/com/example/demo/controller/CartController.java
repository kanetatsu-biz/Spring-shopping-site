package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

@Controller
public class CartController {

	@Autowired
	Cart cart;

	@Autowired
	ItemRepository itemRepository;

	// カート一覧表示
	@GetMapping("/cart")
	public String index() {

		return "cart";
	}

	//	カート追加
	//	（一旦数量は１で固定）
	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam("itemId") int itemId,
			@RequestParam(name = "quantity", defaultValue = "1") Integer quantity) {

		//	カート内の既存の商品をOptional型で取得
		Optional<Item> existItemOpt = cart.getExistItemOpt(itemId);

		//	すでに同じ商品がカートにあった場合
		if (existItemOpt.isPresent()) {
			//	型変換し、数量を更新
			Item existItem = existItemOpt.get();
			existItem.setQuantity(existItem.getQuantity() + quantity);
		} else {
			//	カートに存在しない場合
			//	商品情報を取得後数量をセットし、カートに追加
			Item item = itemRepository.findById(itemId).get();
			item.setQuantity(quantity);
			cart.add(item);
		}

		// カート一覧画面にリダイレクト
		return "redirect:/cart";
	}
}
