package com.example.demo.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SearchService {
	//	文字列検索用のマッチパターン
	public static Map<String, String> stringMatchPatterns = new LinkedHashMap<String, String>() {
		{
			put("partial", "部分一致");
			put("all", "完全一致");
			put("starting", "前方一致");
			put("ending", "後方一致");
		}
	};

	//	マッチパターンに基づき指定された文字列にクエリ用の「％」を付与
	public static String getValueWithWildcard(String value, String matchPattern) {
		String valueWithWildcard = value;

		//	指定された文字列が空の場合はそのまま返す
		if (!valueWithWildcard.equals("")) {
			switch (matchPattern) {
			//	完全一致
			case "all":
				break;
			//	部分一致
			case "partial":
				valueWithWildcard = '%' + valueWithWildcard + '%';
				break;
			//	前方一致
			case "starting":
				valueWithWildcard = valueWithWildcard + '%';
				break;
			//	後方一致
			case "ending":
				valueWithWildcard = '%' + valueWithWildcard;
				break;
			}
		}

		return valueWithWildcard;
	}
}
