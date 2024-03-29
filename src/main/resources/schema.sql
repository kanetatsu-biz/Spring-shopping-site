-- 各種ビュー削除
DROP VIEW IF EXISTS v_order_history_details;
DROP VIEW IF EXISTS v_order_histories;
DROP VIEW IF EXISTS v_login_user_order_history_details;
DROP VIEW IF EXISTS v_login_user_order_histories;

-- 各種テーブル削除
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS wishes;
DROP TABLE IF EXISTS account_addresses;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS accounts;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS categories;

-- カテゴリーテーブル
CREATE TABLE categories
(
   id SERIAL PRIMARY KEY,
   name TEXT
);

-- 商品テーブル
CREATE TABLE items
(
   id SERIAL PRIMARY KEY,
   category_id INTEGER NOT NULL,
   name TEXT UNIQUE NOT NULL,
   price INTEGER NOT NULL,
   description TEXT,
   stock INTEGER NOT NULL,
   file_name VARCHAR(100),
   is_deleted BOOLEAN,
   FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- アカウントテーブル
CREATE TABLE accounts
(
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT UNIQUE,
   password TEXT,
   role VARCHAR(10) DEFAULT 'general'
);

-- あて先テーブル
CREATE TABLE addresses
(
   id SERIAL PRIMARY KEY,
   post_num TEXT NOT NULL,
   prefecture TEXT NOT NULL,
   municipality TEXT NOT NULL,
   house_num TEXT NOT NULL,
   building_name_room_num TEXT
);

-- あて先とアカウントの中間テーブル
CREATE TABLE account_addresses
(
   account_id INTEGER,
   address_id INTEGER,
   address_name VARCHAR(100),
   PRIMARY KEY (account_id, address_id),
   FOREIGN KEY (account_id) REFERENCES accounts(id),
   FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- ほしい物テーブル
CREATE TABLE wishes
(
	customer_id INTEGER,
	item_id INTEGER,
	FOREIGN KEY (customer_id) REFERENCES accounts(id),
	FOREIGN KEY (item_id) REFERENCES items(id)
);

-- 注文テーブル
CREATE TABLE orders
(
   id SERIAL PRIMARY KEY,
   customer_id INTEGER,
   ordered_datetime TIMESTAMP,
   total_price INTEGER,
   address_id INTEGER,
   FOREIGN KEY (customer_id) REFERENCES accounts(id),
   FOREIGN KEY (address_id) REFERENCES addresses(id)
);

-- 注文詳細テーブル
CREATE TABLE order_details
(
   id SERIAL PRIMARY KEY,
   order_id INTEGER,
   item_id INTEGER,
   quantity INTEGER,
   FOREIGN KEY (order_id) REFERENCES orders(id),
   FOREIGN KEY (item_id) REFERENCES items(id)
);

-- 注文履歴一覧ビュー
CREATE VIEW v_order_histories AS
(
   SELECT
      o.id,
      COALESCE(ac.name, 'ゲスト') AS acoount_name,
      to_char(o.ordered_datetime, 'YYYY-MM-DD HH24:MI:SS') AS ordered_datetime,
      o.total_price,
      ad.post_num,
      ad.prefecture,
      ad.municipality,
      ad.house_num,
      ad.building_name_room_num
   FROM orders o
   LEFT JOIN accounts ac
      ON o.customer_id = ac.id
   JOIN addresses ad
      ON o.address_id = ad.id
);

-- 注文履歴詳細ビュー
CREATE VIEW v_order_history_details AS
(
   SELECT
      od.id,
      od.order_id,
      i.name AS item_name,
      i.price AS item_price,
      i.file_name,
      od.quantity
   FROM order_details od
   JOIN items i
      ON od.item_id = i.id
);

-- 一般ユーザー注文履歴一覧ビュー
CREATE VIEW v_login_user_order_histories AS
(
   SELECT
      o.id,
      o.customer_id,
      to_char(o.ordered_datetime, 'YYYY-MM-DD HH24:MI:SS') AS ordered_datetime,
      o.total_price,
      ad.post_num,
      ad.prefecture,
      ad.municipality,
      ad.house_num,
      ad.building_name_room_num
   FROM orders o
   LEFT JOIN accounts ac
      ON o.customer_id = ac.id
   JOIN addresses ad
      ON o.address_id = ad.id
);

-- 注文履歴詳細ビュー
CREATE VIEW v_login_user_order_history_details AS
(
   SELECT
      od.id,
      od.order_id,
      i.name AS item_name,
      i.price AS item_price,
      i.description AS item_description, 
      i.file_name,
      od.quantity
   FROM order_details od
   JOIN items i
      ON od.item_id = i.id
);
