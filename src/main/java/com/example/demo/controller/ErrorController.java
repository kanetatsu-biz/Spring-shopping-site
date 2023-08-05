package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	// 管理画面TOPを表示
	@GetMapping("/error403")
	public String index() {

		return "error403";
	}
}
