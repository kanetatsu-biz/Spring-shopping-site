package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.AccountAddress;
import com.example.demo.entity.Address;
import com.example.demo.model.LoginUser;
import com.example.demo.repository.AccountAddressRepository;
import com.example.demo.repository.AddressRepository;

@Controller
public class AddressController {

	@Autowired
	AccountAddressRepository accountAddressRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	LoginUser loginUser;

	// あて先一覧画面を表示
	@GetMapping("/addresses")
	public String index(
			@RequestParam(name = "successMes", defaultValue = "") String successMes,
			Model model) {

		//	ログインユーザーに紐づくあて先リストを取得
		List<AccountAddress> accountAddressList = accountAddressRepository.findByAccountId(loginUser.getId());
		model.addAttribute("accountAddressList", accountAddressList);

		model.addAttribute("successMes", successMes);

		return "addresses";
	}

	// あて先追加画面を表示
	@GetMapping("/addresses/add")
	public String addAddress(
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			@RequestParam(name = "addressName", defaultValue = "") String addressName,
			@ModelAttribute("inputAddress") Address inputAddress,
			Model model) {

		model.addAttribute("errMes", errMes);
		model.addAttribute("prefectureList", Address.prefectureList); // 都道府県リスト
		model.addAttribute("addressName", addressName);
		model.addAttribute("address", inputAddress == null ? new Address() : inputAddress);

		return "addAddress";
	}

	// あて先追加画面を表示
	@PostMapping("/addresses/add")
	public String createAddress(
			@RequestParam(name = "addressName", defaultValue = "") String addressName,
			RedirectAttributes redirectAttributes,
			@ModelAttribute Address inputAddress, // 入力値をそのままオブジェクトに詰める
			Model model) {

		//	必須のバリデーション
		if (inputAddress.getPostNum().equals("") ||
				inputAddress.getPrefecture().equals("") ||
				inputAddress.getMunicipality().equals("") ||
				inputAddress.getHouseNum().equals("")) {
			//	入力値とエラーメッセージをリダイレクト先に送る
			redirectAttributes.addFlashAttribute("inputAddress", inputAddress);
			redirectAttributes.addAttribute("errMes",
					"「建物名・部屋番号」以外は全て必須項目です。");
			redirectAttributes.addAttribute("addressName", addressName);
			return "redirect:/addresses/add";
		}

		//	１，あて先テーブルに保存し、新規登録したあて先情報を取得
		Address savedAddress = addressRepository.save(inputAddress);

		//	２，ユーザーとの中間テーブルに保存
		accountAddressRepository.save(new AccountAddress(
				loginUser.getId(), savedAddress.getId(), addressName));

		//	３，登録が完了した旨を一覧画面に送る
		redirectAttributes.addAttribute("successMes", "あて先の新規登録が完了しました。");

		return "redirect:/addresses";
	}
}
