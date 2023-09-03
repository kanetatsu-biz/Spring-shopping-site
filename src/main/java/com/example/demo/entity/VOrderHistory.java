package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// 注文履歴表示用ビュー
@Entity
@Table(name = "v_order_histories")
public class VOrderHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 注文ID

	@Column(name = "acoount_name")
	private String acoountName; // アカウント名

	@Column(name = "ordered_datetime")
	private LocalDateTime orderedDatetime; // 注文日時

	@Column(name = "total_price")
	private Integer totalPrice; // 合計金額

	@Column(name = "post_num")
	private String postNum; // 郵便番号

	private String prefecture; // 都道府県
	private String municipality; // 市区町村

	@Column(name = "house_num")
	private String houseNum; // 番地

	@Column(name = "building_name_room_num")
	private String buildingNameRoomNum; // 建物名・部屋番号

	public Integer getId() {
		return id;
	}

	public String getAcoountName() {
		return acoountName;
	}

	public LocalDateTime getOrderedDatetime() {
		return orderedDatetime;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public String getPostNum() {
		return postNum;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public String getMunicipality() {
		return municipality;
	}

	public String getHouseNum() {
		return houseNum;
	}

	public String getBuildingNameRoomNum() {
		return buildingNameRoomNum;
	}
}
