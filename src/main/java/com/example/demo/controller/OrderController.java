package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

	// 注文画面表示
	@GetMapping("/order")
	public String index() {

		return "order";
	}

	// 注文確認画面表示
	@GetMapping("/order/confirm")
	public String confirm() {

		return "orderConfirm";
	}
}
