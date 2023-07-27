package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VOrderHistoryDetail;

public interface VOrderHistoryDetailRepository extends JpaRepository<VOrderHistoryDetail, Integer> {
	// SELECT * FROM v_order_history_details WHERE order_id = ?
	List<VOrderHistoryDetail> findByOrderId(Integer orderId);
}
