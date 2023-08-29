'use strict';

//渡ってきた商品IDを元に数量を変更処理→戻ってきた値を元にhtmlを書き換え
function changeQuantity(selected_id, quantity) {
	const url = '/cart/update/' +  selected_id;
	const target = document.getElementById("cart-items");

	//変更された数量をpost形式で渡す
    $.ajax({
		method: 'POST',
		data: {quantity},
		url: url
	}).then(
		//戻り値でhtmlを書き換え
		function(data) {
			target.outerHTML = data;
		},
		function() {
			alert("error");
		}
	);
}

//　商品一覧にSlickを適用（完全にjqueryのScriptが読み込めてから実行）
$(document).ready(function() {
    $('.item-list-slider').slick({
        centerMode: true, // 前後の要素を表示
        dots: true, // インジケーターの表示
        infinite: true, // 無限スクロール
		responsive: [
	      {
	        breakpoint: 1025, // 1024px以下のサイズに適用
	        settings: {
	        	slidesToShow: 2,
	        },
	      },
	      {
	        breakpoint: 750, // 749px以下のサイズに適用
	        settings: {
	        	slidesToShow: 1,
	        },
	      },
	    ],
        slidesToShow: 3,
        swipeToSlide: true // スワイプでの移動も可
    });
});

//　指定されたあて先IDに紐づくあて先情報に書き換え
function changeAddress(selected_address_id) {
	const url = '/addresses/' +  selected_address_id + '/info';
	const target = document.getElementById("address-form");

	//変更された数量をpost形式で渡す
    $.ajax({
		method: 'GET',
		data: {},
		url: url
	}).then(
		//戻り値でhtmlを書き換え
		function(data) {
			target.outerHTML = data;
		},
		function() {
			alert("error");
		}
	);
}
