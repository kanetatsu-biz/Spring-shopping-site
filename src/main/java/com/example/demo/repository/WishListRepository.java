package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.WishList;
import com.example.demo.entity.WishListKey;

import jakarta.transaction.Transactional;

public interface WishListRepository extends JpaRepository<WishList, WishListKey> {

	//ほしい物テーブルから対象のユーザーのほしい物を取得
	List<WishList> findByCustomerId(Integer customerId);

	//すでにほしい物リストに追加されているかチェック
	boolean existsByCustomerIdAndItemId(Integer customerId, Integer itemId);

	//ほしい物テーブルから指定されたほしい物を削除
	// DELETE FROM wishes WHERE customer = ? AND item_id = ?
	@Transactional
	void deleteByCustomerIdAndItemId(Integer customerId, Integer itemId);

}
