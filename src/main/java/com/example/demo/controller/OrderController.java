package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.entity.VLoginUserOrderHistory;
import com.example.demo.entity.VLoginUserOrderHistoryDetail;
import com.example.demo.entity.VOrderHistory;
import com.example.demo.entity.VOrderHistoryDetail;
import com.example.demo.model.Cart;
import com.example.demo.model.LoginUser;
import com.example.demo.repository.AccountAddressRepository;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.VLoginUserOrderHistoryDetailRepository;
import com.example.demo.repository.VLoginUserOrderHistoryRepository;
import com.example.demo.repository.VOrderHistoryDetailRepository;
import com.example.demo.repository.VOrderHistoryRepository;
import com.example.demo.service.validation.AddressValidationService;

@Controller
public class OrderController {

	@Autowired
	Cart cart;

	@Autowired
	LoginUser loginUser;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	AccountAddressRepository accountAddressRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Autowired
	VOrderHistoryRepository vOrderHistoryRepository;

	@Autowired
	VOrderHistoryDetailRepository vOrderHistoryDetailRepository;

	@Autowired
	VLoginUserOrderHistoryRepository vLoginUserOrderHistoryRepository;

	@Autowired
	VLoginUserOrderHistoryDetailRepository vLoginUserOrderHistoryDetailRepository;

	// 注文画面表示
	@GetMapping("/order")
	public String index(
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			@RequestParam(name = "selectedAddressId", defaultValue = "0") Integer selectedAddressId,
			@ModelAttribute("inputAddress") Address inputAddress,
			@RequestParam(name = "redrawComponent", defaultValue = "") String redrawComponent,
			Model model) {

		model.addAttribute("prefectureList", Address.prefectureList); // 都道府県のリスト
		model.addAttribute("errMes", errMes);
		model.addAttribute("selectedAddressId", selectedAddressId); // 選択されたあて先ID

		//	ログインユーザーのあて先リストを取得
		List<AccountAddress> accountAddressList = loginUser.isLogin()
				? accountAddressRepository.findByAccountIdOrderByAddressName(loginUser.getId())
				: null;
		model.addAttribute("accountAddressList", accountAddressList);

		//	既に入力されていた場合は入力値を保持する
		model.addAttribute("address", inputAddress == null ? new Address() : inputAddress);

		return redrawComponent.equals("") ? "order" : "order :: " + redrawComponent;
	}

	// 指定されたあて先の情報を取得し、画面を再描画
	@GetMapping("/addresses/{addressId}/info")
	public String getAddress(
			@PathVariable("addressId") Integer addressId,
			RedirectAttributes redirectAttributes) {

		//	手入力が選択された場合はスルー
		if (addressId != 0) {
			//	あて先情報を取得
			AccountAddress selectedAccountAddress = accountAddressRepository.findByAccountIdAndAddressId(
					loginUser.getId(), addressId).get();
			redirectAttributes.addFlashAttribute("inputAddress", selectedAccountAddress.getAddress());
		}
		redirectAttributes.addAttribute("selectedAddressId", addressId);

		//	再描画する箇所を指定
		redirectAttributes.addAttribute("redrawComponent", "address-form");

		return "redirect:/order";
	}

	// 注文確認画面表示
	@PostMapping("/order/confirm")
	public String confirm(
			RedirectAttributes redirectAttributes,
			@ModelAttribute Address inputAddress, // 入力値をそのままオブジェクトに詰める
			Model model) {

		//	必須のバリデーション
		if (AddressValidationService.validateRequiredFields(inputAddress)) {
			//	入力値とエラーメッセージをリダイレクト先に送る
			redirectAttributes.addFlashAttribute("inputAddress", inputAddress);
			redirectAttributes.addAttribute("errMes",
					"「建物名・部屋番号」以外は全て必須項目です。");
			return "redirect:/order";
		}

		model.addAttribute("confirmedAddress", inputAddress);

		return "orderConfirm";
	}

	// 注文確定処理実行後、完了画面表示
	@PostMapping("/order")
	public String order(
			@ModelAttribute Address confirmedAddress,
			Model model) {
		//	１，あて先テーブルに保存
		Address savedAddress = addressRepository.save(confirmedAddress);

		//	２，注文テーブルに保存
		//	（一旦顧客IDはセッションから取得）
		Order newOrder = new Order(
				loginUser.getId(),
				LocalDateTime.now(),
				cart.getTotalPrice(),
				savedAddress.getId());
		Order savedOrder = orderRepository.save(newOrder);

		//	３，注文詳細テーブルに保存
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (Item item : cart.getItems()) {
			orderDetails.add(
					new OrderDetail(
							savedOrder.getId(),
							item.getId(),
							item.getQuantity()));
		}
		orderDetailRepository.saveAll(orderDetails);

		//	４，カート内を全削除
		cart.getItems().clear();

		//	５，注文完了画面に注文IDを渡して表示
		model.addAttribute("orderId", savedOrder.getId());

		return "ordered";
	}

	// （管理）注文履歴一覧画面表示
	@GetMapping("/admin/order/histories")
	public String histories(Model model) {

		//	全ユーザーの注文履歴を取得し、画面に渡す
		List<VOrderHistory> orderList = vOrderHistoryRepository.findAll();
		model.addAttribute("orderList", orderList);

		return "histories";
	}

	// （管理）注文履歴詳細画面表示
	@GetMapping("/admin/order/histories/{orderId}")
	public String showHistory(
			@PathVariable("orderId") Integer orderId,
			Model model) {

		//	１，指定された注文履歴の大まかな情報を取得し、画面に渡す
		VOrderHistory orderInfo = vOrderHistoryRepository.findById(orderId).get();
		model.addAttribute("orderInfo", orderInfo);

		//	２，指定された注文履歴の詳細情報を取得し、画面に渡す
		List<VOrderHistoryDetail> orderHistoryDetailList = vOrderHistoryDetailRepository.findByOrderId(orderId);
		model.addAttribute("orderHistoryDetailList", orderHistoryDetailList);

		return "showHistory";
	}

	// （一般ユーザー）注文履歴一覧画面表示
	@GetMapping("/loginUser/order/histories")
	public String loginUserOrderHistories(
			Model model) {

		Integer customerId = loginUser.getId();

		//	ログインしているユーザーの注文履歴を取得し、画面に渡す
		List<VLoginUserOrderHistory> orderList = vLoginUserOrderHistoryRepository
				.findByCustomerId(customerId);
		model.addAttribute("loginUserOrderList", orderList);

		return "loginUserOrderHistories";
	}

	// （一般ユーザー）注文履歴詳細画面表示
	@GetMapping("/loginUser/order/history/{orderId}")
	public String loginUserShowHistory(
			@PathVariable("orderId") Integer orderId,
			Model model) {

		//	１，指定された注文履歴の大まかな情報を取得し、画面に渡す
		VLoginUserOrderHistory loginUserorderInfo = vLoginUserOrderHistoryRepository.findById(orderId).get();
		model.addAttribute("loginUserOrderInfo", loginUserorderInfo);

		//	２，指定された注文履歴の詳細情報を取得し、画面に渡す
		List<VLoginUserOrderHistoryDetail> loginUserOrderHistoryDetailList = vLoginUserOrderHistoryDetailRepository
				.findByOrderId(orderId);
		model.addAttribute("loginUserOrderHistoryDetailList", loginUserOrderHistoryDetailList);

		return "loginUserShowOrderHistory";
	}
}
