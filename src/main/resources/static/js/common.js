'use strict';

var head = document.getElementsByTagName('head')
var script = document.createElement('script')
//ここで任意のjQueryを読み込ませる
script.setAttribute('src', 'https://code.jquery.com/jquery-3.7.0.js')
script.setAttribute('type', 'text/javascript')
document.head.appendChild(script)


	function changeQuantity(selected_id, quantity) {
		console.log(selected_id);
		console.log(quantity);
    	const url = '/cart/update/' +  selected_id;
    	const target = document.getElementById("cart-items");

    $.ajax({
      method: 'POST',
      data: {quantity},
      url: url
    }).then(
            function(data) {
              target.outerHTML = data;
            },
            function() {
              alert("error");
            });
  }
 