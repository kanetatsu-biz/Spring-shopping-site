package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "v_login_user_order_histories")
public class VLoginUserOrderHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 注文ID

	@Column(name = "customer_id")
	private Integer customerId; // アカウントID

	@Column(name = "ordered_datetime")
	private String orderedDatetime; // 注文日時

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

	public Integer getCustomerId() {
		return customerId;
	}

	public String getOrderedDatetime() {
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
