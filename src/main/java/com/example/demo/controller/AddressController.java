package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.AccountAddress;
import com.example.demo.entity.Address;
import com.example.demo.model.LoginUser;
import com.example.demo.repository.AccountAddressRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.service.validation.AddressValidationService;

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
		List<AccountAddress> accountAddressList = accountAddressRepository
				.findByAccountIdOrderByAddressName(loginUser.getId());
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

	// あて先新規登録処理
	@PostMapping("/addresses/create")
	public String createAddress(
			@RequestParam(name = "addressName", defaultValue = "") String addressName,
			RedirectAttributes redirectAttributes,
			@ModelAttribute Address inputAddress, // 入力値をそのままオブジェクトに詰める
			Model model) {

		String errMes = ""; // エラーメッセージ

		//	１－１，必須のバリデーション
		if (AddressValidationService.validateRequiredFields(inputAddress)) {
			errMes = "「建物名・部屋番号」以外は全て必須項目です。";
		}
		//	１－２，1人のユーザーに対してのあて先名の重複のバリデーション
		else if (accountAddressRepository.existsByAccountIdAndAddressName(loginUser.getId(), addressName)) {
			errMes = "そのあて先名は既に登録済みです。";
		}

		//	１－３，バリデーションにはじかれた場合
		if (!errMes.equals("")) {
			//	入力値とエラーメッセージをリダイレクト先に送る
			redirectAttributes.addFlashAttribute("inputAddress", inputAddress);
			redirectAttributes.addAttribute("errMes", errMes);
			redirectAttributes.addAttribute("addressName", addressName);
			return "redirect:/addresses/add";
		}

		//	２－１，あて先テーブルに保存し、新規登録したあて先情報を取得
		Address savedAddress = addressRepository.save(inputAddress);

		//	２－２，ユーザーとの中間テーブルに保存
		accountAddressRepository.save(new AccountAddress(
				loginUser.getId(), savedAddress.getId(), addressName));

		//	２－３，登録が完了した旨を一覧画面に送る
		redirectAttributes.addAttribute("successMes", "あて先の新規登録が完了しました。");

		return "redirect:/addresses";
	}

	// あて先編集画面を表示
	@GetMapping("/addresses/{addressId}/edit")
	public String index(
			@PathVariable("addressId") Integer addressId,
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			@RequestParam(name = "addressName", defaultValue = "") String addressName,
			@ModelAttribute("inputAccountAddress") AccountAddress inputAccountAddress,
			Model model) {

		AccountAddress accountAddress = null;

		//	初期表示の場合
		if (errMes.equals("")) {
			//	ログインユーザーとあて先IDに紐づくあて先を取得
			accountAddress = accountAddressRepository
					.findByAccountIdAndAddressId(loginUser.getId(), addressId).get();
		} else {
			//	バリデーションではじかれた場合は、前回の入力値を保持
			accountAddress = inputAccountAddress;
		}

		model.addAttribute("accountAddress", accountAddress);
		model.addAttribute("prefectureList", Address.prefectureList); // 都道府県リスト
		model.addAttribute("errMes", errMes);

		return "editAddress";
	}

	// あて先更新処理
	@PostMapping("/addresses/{addressId}/update")
	public String createAddress(
			@PathVariable("addressId") Integer addressId,
			@RequestParam(name = "addressName", defaultValue = "") String addressName,
			RedirectAttributes redirectAttributes,
			@ModelAttribute Address inputAddress, // あて先名以外の入力値をそのままオブジェクトに詰める
			Model model) {

		//	入力値をもとに中間テーブルのオブジェクトを作成
		AccountAddress inputAccountAddress = new AccountAddress(
				loginUser.getId(), addressId, addressName, inputAddress);

		//	更新前のあて先と変わっているか判別するための条件（大文字区別ありの完全一致）
		ExampleMatcher exactMatcher = ExampleMatcher.matching()
				.withIgnoreCase(false)
				.withStringMatcher(StringMatcher.EXACT);
		Example<AccountAddress> inputAccountAddressExample = Example.of(inputAccountAddress, exactMatcher);

		//	あて先名とあて先の内容（Query by Exampleを用いて確認）が変更されている場合は更新処理をスルー
		if (!accountAddressRepository.exists(inputAccountAddressExample)) {

			String errMes = ""; // エラーメッセージ

			//	１－１，必須のバリデーション
			if (AddressValidationService.validateRequiredFields(inputAddress)) {
				errMes = "「建物名・部屋番号」以外は全て必須項目です。";
			}
			//	１－２，1人のユーザーに対して、他のあて先名との重複のバリデーション
			else if (accountAddressRepository.existsByAccountIdAndAddressNameAndAddressIdNot(
					loginUser.getId(), addressName, addressId)) {
				errMes = "このあて先名は既に他で登録済みです。";
			}

			//	１－３，バリデーションにはじかれた場合
			if (!errMes.equals("")) {
				//	入力値とエラーメッセージをリダイレクト先に送る
				redirectAttributes.addFlashAttribute("inputAccountAddress", inputAccountAddress);
				redirectAttributes.addAttribute("errMes", errMes);
				redirectAttributes.addAttribute("addressName", addressName);
				return "redirect:/addresses/" + addressId + "/edit";
			}

			//	あて先だけの条件
			Example<Address> inputAddressExample = Example.of(inputAddress, exactMatcher);

			//	あて先名を変更しただけの場合
			if (addressRepository.exists(inputAddressExample)) {
				//	２－１，既存のユーザーとの中間テーブルのデータを取得
				AccountAddress updateAccountAddress = accountAddressRepository.findByAccountIdAndAddressId(
						loginUser.getId(), addressId).get();

				//	２－２，あて先名のみ変更して更新
				updateAccountAddress.setAddressName(addressName);
				accountAddressRepository.save(updateAccountAddress);
			} else {
				//	２－１，あて先テーブルに保存し、新規登録したあて先情報を取得
				Address savedAddress = addressRepository.save(inputAddress);

				//	２－２，既存のユーザーとの中間テーブルのデータを削除
				//	※複合主キーのため、あて先IDのみの変更はできない
				accountAddressRepository.deleteByAccountIdAndAddressId(loginUser.getId(), addressId);

				//	２－３，あて先IDのみ変更して新規登録
				inputAccountAddress.setAddressId(savedAddress.getId());
				accountAddressRepository.save(inputAccountAddress);
			}
		}

		//	更新が完了した旨を一覧画面に送る
		redirectAttributes.addAttribute("successMes", "あて先の更新が完了しました。");

		return "redirect:/addresses";
	}
}
