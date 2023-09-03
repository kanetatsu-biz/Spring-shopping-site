INSERT INTO categories(name) VALUES('本');
INSERT INTO categories(name) VALUES('香り');
INSERT INTO categories(name) VALUES('灯り');

INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(1, 'バラの本', 2500, 'バラの茎から作られた本です', 10, 'book1.jpg', false);
INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(1, 'すぎの本　花粉不使用', 980, '杉の木を薄く刷った素材そのままの本です', 8, 'book2.jpg', false);
INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(1, 'かすみ草とシロツメクサの本　かすみ草の花＋シロツメクサ＋ぺんぺん草の葉', 1200, 'かすみ草の花、シロツメクサ、ぺんぺん草の葉の三種類をブレンドして作られた本です', 6, 'book3.jpg', false);

INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(2, 'ハーブの香り', 2000, 'ハーブのみ使用したシンプルな香りです', 10, 'aroma1.jpg', false);
INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(2, 'オレンジの香り　オレンジ＋カルダモン', 1000, 'オレンジとカルダモンをした、すっきりとした香りです', 8, 'aroma2.jpg', false);
INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(2, 'ハーブシトラスの香り　オレンジ＋ライム＋ハーブ＋ローズマリー', 1800, 'オレンジ、ライムの二種類の柑橘系とハーブ、ローズマリーの二種類のハーブ系の香りを混ぜたリラックス効果のある香りです', 6, 'aroma3.jpg', false);

INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(3, '白樺の灯り', 780, 'ふんわりとした灯りです', 10, 'light1.jpg', false);
INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(3, 'オークの灯り　ブラックオーク', 3400, '夕方のような、ロートーンの灯りです', 8, 'light2.jpg', false);
INSERT INTO items(category_id, name, price, description, stock, file_name, is_deleted) VALUES(3, 'マツの灯り　国産松＋アカマツ＋クロマツ', 2200, '三種類の松を使用した香り豊かなライトです。木漏れ日の中にいるような灯りです', 6, 'light3.jpg', false);

INSERT INTO accounts(name, email, password) VALUES('test1', 'test1@example.com', 'password');
INSERT INTO accounts(name, email, password) VALUES('test2', 'test2@example.com', 'password');
INSERT INTO accounts(name, email, password, role) VALUES('admin', 'admin@example.com', 'admin', 'admin');
INSERT INTO accounts(name, email, password, role) VALUES('system', 'system@example.com', 'system', 'system');

INSERT INTO addresses(post_num, prefecture, municipality, house_num, building_name_room_num)
	VALUES ('110-0011', '東京都', '千代田区千代田', '1番1号', '皇居101');

INSERT INTO addresses(post_num, prefecture, municipality, house_num, building_name_room_num)
	VALUES ('110-0011', '東京都', '千代田区千代田', '1番1号', '皇居102');

INSERT INTO addresses(post_num, prefecture, municipality, house_num, building_name_room_num)
	VALUES ('110-0011', '東京都', '千代田区千代田', '1番1号', '皇居103');

INSERT INTO public.account_addresses(account_id, address_id, address_name)
	VALUES (1, 1, '自宅');

INSERT INTO public.account_addresses(account_id, address_id, address_name)
	VALUES (1, 2, '職場');

INSERT INTO public.account_addresses(account_id, address_id, address_name)
	VALUES (2, 1, '自宅');

INSERT INTO orders(customer_id, ordered_datetime, total_price, address_id)
	VALUES (NULL, '2023-07-27 12:00:00.000000', 5000, 1);
INSERT INTO order_details(order_id, item_id, quantity) VALUES (1, 1, 2);

INSERT INTO orders(customer_id, ordered_datetime, total_price, address_id)
	VALUES (1, '2023-07-27 15:00:00.000000', 4400, 1);
INSERT INTO order_details(order_id, item_id, quantity) VALUES (2, 3, 2);
INSERT INTO order_details(order_id, item_id, quantity) VALUES (2, 4, 1);
