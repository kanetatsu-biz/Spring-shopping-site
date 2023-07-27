package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.VOrderHistory;

public interface VOrderRepository extends JpaRepository<VOrderHistory, Integer> {
}
