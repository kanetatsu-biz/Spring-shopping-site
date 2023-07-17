package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Address;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.model.Cart;
import com.example.demo.repository.OrderDetailRepository;
import com.example.demo.repository.OrderRepository;

@Controller
public class OrderController {

	@Autowired
	Cart cart;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	// 注文画面表示
	@GetMapping("/order")
	public String index(
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			@ModelAttribute("inputAddress") Address inputAddress,
			Model model) {

		//	都道府県の配列を画面に渡す
		model.addAttribute("prefectureList", new String[] {
				"北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県",
				"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県",
				"新潟県", "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県",
				"静岡県", "愛知県", "三重県", "滋賀県", "京都府", "大阪府", "兵庫県",
				"奈良県", "和歌山県", "鳥取県", "島根県", "岡山県", "広島県", "山口県",
				"徳島県", "香川県", "愛媛県", "高知県", "福岡県", "佐賀県", "長崎県",
				"熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県" });
		model.addAttribute("errMes", errMes);

		//	既に入力されていた場合は入力値を保持する
		model.addAttribute("address", inputAddress == null ? new Address() : inputAddress);

		return "order";
	}

	// 注文確認画面表示
	@PostMapping("/order/confirm")
	public String confirm(
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
		//	注文テーブルに保存
		//	（一旦顧客IDは固定）
		Order newOrder = new Order(
				1,
				LocalDateTime.now(),
				cart.getTotalPrice());
		Order savedOrder = orderRepository.save(newOrder);

		//	注文詳細テーブルに保存
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (Item item : cart.getItems()) {
			orderDetails.add(
					new OrderDetail(
							savedOrder.getId(),
							item.getId(),
							item.getQuantity()));
		}
		orderDetailRepository.saveAll(orderDetails);

		//	カート内を全削除
		cart.getItems().clear();

		//	注文完了画面に注文IDを渡して表示
		model.addAttribute("orderId", savedOrder.getId());

		return "ordered";
	}
}
