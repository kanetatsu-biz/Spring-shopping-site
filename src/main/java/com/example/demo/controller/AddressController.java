package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.AccountAddress;
import com.example.demo.model.LoginUser;
import com.example.demo.repository.AccountAddressRepository;

@Controller
public class AddressController {

	@Autowired
	AccountAddressRepository accountAddressRepository;

	@Autowired
	LoginUser loginUser;

	// あて先一覧画面を表示
	@GetMapping("/addresses")
	public String index(Model model) {

		//	ログインユーザーに紐づくあて先リストを取得
		List<AccountAddress> accountAddressList = accountAddressRepository.findByAccountId(loginUser.getId());
		model.addAttribute("accountAddressList", accountAddressList);

		return "addresses";
	}
}
