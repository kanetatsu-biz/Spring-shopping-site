package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class wishListController {

	// ほしい物リスト一覧表示
	@GetMapping("/wishList")
	public String index() {

		return "wishList";
	}

	@GetMapping("/wishList/add")
	public String add() {

		return "wishList";
	}

}
