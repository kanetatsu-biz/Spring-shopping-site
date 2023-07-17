package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // あて先ID
	private String postNum; // 郵便番号
	private String prefecture; // 都道府県
	private String municipality; // 市区町村
	private String houseNum; // 番地
	private String buildingNameRoomNum; // 建物名・部屋番号

	public Integer getId() {
		return id;
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

	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

	public void setBuildingNameRoomNum(String buildingNameRoomNum) {
		this.buildingNameRoomNum = buildingNameRoomNum;
	}

	//	指定された値が既にセットされている値と同じか
	public boolean isPrefectureSet(String prefecture) {
		return this.prefecture != null && this.prefecture.equals(prefecture);
	}
}
