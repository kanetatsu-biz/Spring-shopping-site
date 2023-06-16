package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Account;
import com.example.demo.model.LoginUser;
import com.example.demo.repository.AccountRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	LoginUser loginUser;

	// ログイン画面を表示
	@GetMapping({ "/", "/login" })
	public String loginForm(
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			@RequestParam(name = "successMes", defaultValue = "") String successMes,
			Model model) {

		//	リダイレクト用
		model.addAttribute("email", email);
		model.addAttribute("errMes", errMes);
		model.addAttribute("successMes", successMes);

		return "loginForm";
	}

	// 入力データをもとにログイン処理
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes,
			Model model) {

		//	必須チェック
		if (email.length() == 0 || password.length() == 0) {
			//	入力値はそのままにし、エラーメッセージをリダイレクト先に渡す
			redirectAttributes.addAttribute("email", email);
			redirectAttributes.addAttribute("errMes", "どちらも必須項目です。");

			return "redirect:/login";
		}

		//	メアドをもとにアカウント情報を取得
		Optional<Account> existAccountOpt = accountRepository.findByEmail(email);

		//	アカウント存在チェック
		if (!existAccountOpt.isPresent()) {
			//	入力値はそのままにし、エラーメッセージをリダイレクト先に渡す
			redirectAttributes.addAttribute("email", email);
			redirectAttributes.addAttribute("errMes", "存在しないアカウントです。");

			return "redirect:/login";
		}

		//	DBから取得したログインユーザ情報を取得
		Account existAccount = existAccountOpt.get();

		//	パスワードと一致しているかチェック
		if (!password.equals(existAccount.getPassword())) {
			//	入力値はそのままにし、エラーメッセージをリダイレクト先に渡す
			redirectAttributes.addAttribute("email", email);
			redirectAttributes.addAttribute("errMes", "パスワードが一致しません。");

			return "redirect:/login";
		}

		//	セッションにログインユーザ情報を詰める
		loginUser.setId(existAccount.getId());
		loginUser.setName(existAccount.getName());

		//	商品一覧画面にリダイレクト
		return "redirect:/items";
	}

	// ログアウト
	@GetMapping("/logout")
	public String logout() {
		//	セッションの中身をリセット
		session.invalidate();

		return "redirect:/login";
	}

	// 新規登録画面を表示
	@GetMapping("/register")
	public String registerForm(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			Model model) {

		//	リダイレクト用
		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("errMes", errMes);

		return "registerForm";
	}

	// 入力データをもとに新規登録処理
	@PostMapping("/register")
	public String register(
			@RequestParam("name") String name,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			RedirectAttributes redirectAttributes,
			Model model) {

		//	必須チェック
		if (name.length() == 0 || email.length() == 0 || password.length() == 0) {
			//	入力値はそのままにし、エラーメッセージをリダイレクト先に渡す
			redirectAttributes.addAttribute("name", name);
			redirectAttributes.addAttribute("email", email);
			redirectAttributes.addAttribute("errMes", "全て必須項目です。");

			return "redirect:/register";
		}

		//	メアドをもとにアカウント情報を取得
		Optional<Account> existAccountOpt = accountRepository.findByEmail(email);

		//	アカウント存在チェック
		if (existAccountOpt.isPresent()) {
			//	入力値はそのままにし、エラーメッセージをリダイレクト先に渡す
			redirectAttributes.addAttribute("name", name);
			redirectAttributes.addAttribute("email", email);
			redirectAttributes.addAttribute("errMes", "そのメールアドレスはすでに登録済みです。");

			return "redirect:/register";
		}

		//	ユーザ新規登録処理
		Account account = new Account(name, email, password);
		accountRepository.save(account);

		//	登録が完了した旨をリダイレクト先に送る
		redirectAttributes.addAttribute("successMes", "新規ユーザー登録が完了しました。");

		//	ログイン画面にリダイレクト
		return "redirect:/login";
	}
}
