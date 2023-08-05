package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	// アクセス権限エラー画面を表示
	@GetMapping("/error403")
	public String error403() {

		return "error403";
	}
}
