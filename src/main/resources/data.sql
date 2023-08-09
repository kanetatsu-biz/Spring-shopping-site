INSERT INTO categories(name) VALUES('本');
INSERT INTO categories(name) VALUES('DVD');
INSERT INTO categories(name) VALUES('ゲーム');

INSERT INTO items(category_id, name, price, description, file_name) VALUES(1, '本１aaa', 2500, '短い説明です', 'sample_test1.jpg');
INSERT INTO items(category_id, name, price, description, file_name) VALUES(1, '本２bbb　中間くらいの長さ', 980, '中間くらいの長さの説明です', 'sample_test2.png');
INSERT INTO items(category_id, name, price, description, file_name) VALUES(1, '本３ccc　結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです', 1200, '結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です', 'sample_test3.jpg');

INSERT INTO items(category_id, name, price, description, file_name) VALUES(2, 'DVD１AAA', 2000, '短い説明です', 'sample_test1.jpg');
INSERT INTO items(category_id, name, price, description, file_name) VALUES(2, 'DVD２BBB　中間くらいの長さ', 1000, '中間くらいの長さの説明です', 'sample_test2.png');
INSERT INTO items(category_id, name, price, description, file_name) VALUES(2, 'DVD３CCC　結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです', 1800, '結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です', 'sample_test3.jpg');

INSERT INTO items(category_id, name, price, description, file_name) VALUES(3, 'ゲーム１aAa', 780, '短い説明です', 'sample_test1.jpg');
INSERT INTO items(category_id, name, price, description, file_name) VALUES(3, 'ゲーム２BbB　中間くらいの長さ', 3400, '中間くらいの長さの説明です', 'sample_test2.png');
INSERT INTO items(category_id, name, price, description, file_name) VALUES(3, 'ゲーム３ccC　結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです', 2200, '結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です結構長めの説明です', 'sample_test3.jpg');

INSERT INTO accounts(name, email, password) VALUES('test1', 'test1@example.com', 'password');
INSERT INTO accounts(name, email, password) VALUES('test2', 'test2@example.com', 'password');
INSERT INTO accounts(name, email, password, role) VALUES('admin', 'admin@example.com', 'admin', 'admin');
INSERT INTO accounts(name, email, password, role) VALUES('system', 'system@example.com', 'system', 'system');

INSERT INTO addresses(post_num, prefecture, municipality, house_num, building_name_room_num)
	VALUES ('110-0011', '東京都', '千代田区千代田', '1番1号', '皇居101');

INSERT INTO orders(customer_id, ordered_datetime, total_price, address_id)
	VALUES (NULL, '2023-07-27 12:00:00.000000', 5000, 1);
INSERT INTO order_details(order_id, item_id, quantity) VALUES (1, 1, 2);

INSERT INTO orders(customer_id, ordered_datetime, total_price, address_id)
	VALUES (1, '2023-07-27 15:00:00.000000', 4400, 1);
INSERT INTO order_details(order_id, item_id, quantity) VALUES (2, 3, 2);
INSERT INTO order_details(order_id, item_id, quantity) VALUES (2, 4, 1);
