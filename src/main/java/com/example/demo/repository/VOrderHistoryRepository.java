package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VOrderHistory;

public interface VOrderHistoryRepository extends JpaRepository<VOrderHistory, Integer> {
	// SELECT * FROM v_order_histories WHERE id = ?
	Optional<VOrderHistory> findById(Integer orderId);
}
