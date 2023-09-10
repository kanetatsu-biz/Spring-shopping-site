package com.example.demo.entity;

import jakarta.persistence.Column;
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

	@Column(name = "post_num", nullable = false)
	private String postNum; // 郵便番号

	@Column(nullable = false)
	private String prefecture; // 都道府県
	@Column(nullable = false)
	private String municipality; // 市区町村

	@Column(name = "house_num", nullable = false)
	private String houseNum; // 番地

	@Column(name = "building_name_room_num")
	private String buildingNameRoomNum; // 建物名・部屋番号

	//	都道府県のリスト
	public static String[] prefectureList = new String[] {
			"北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県",
			"茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県",
			"新潟県", "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県",
			"静岡県", "愛知県", "三重県", "滋賀県", "京都府", "大阪府", "兵庫県",
			"奈良県", "和歌山県", "鳥取県", "島根県", "岡山県", "広島県", "山口県",
			"徳島県", "香川県", "愛媛県", "高知県", "福岡県", "佐賀県", "長崎県",
			"熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県" };

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
