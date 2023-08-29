'use strict';

const head = document.getElementsByTagName('head');
const script = document.createElement('script');
//任意のjQueryを読み込ませる
script.setAttribute('src', 'https://code.jquery.com/jquery-3.7.0.js');
script.setAttribute('type', 'text/javascript');
document.head.appendChild(script);

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