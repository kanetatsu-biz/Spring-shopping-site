package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VLoginUserOrderHistoryDetail;

public interface VLoginUserOrderHistoryDetailRepository extends JpaRepository<VLoginUserOrderHistoryDetail, Integer> {
	// SELECT * FROM v_loginUser_order_history_details WHERE order_id = ?
	List<VLoginUserOrderHistoryDetail> findByOrderId(Integer orderId);
}
