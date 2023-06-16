package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
	public String index() {

		return "order";
	}

	// 注文確認画面表示
	@GetMapping("/order/confirm")
	public String confirm() {

		return "orderConfirm";
	}

	// 注文確定処理実行後、完了画面表示
	@PostMapping("/order")
	public String order(Model model) {
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
