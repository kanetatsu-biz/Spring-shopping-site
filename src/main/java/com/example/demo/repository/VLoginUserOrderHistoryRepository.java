package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VLoginUserOrderHistory;

public interface VLoginUserOrderHistoryRepository extends JpaRepository<VLoginUserOrderHistory, Integer> {
	// SELECT * FROM v_loginUser_order_histories WHERE id = ?
	List<VLoginUserOrderHistory> findByCustomerId(Integer customerId);
}
