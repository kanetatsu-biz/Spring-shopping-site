package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	// 管理画面TOPを表示
	@GetMapping("/admin")
	public String index() {

		return "adminTop";
	}
}
