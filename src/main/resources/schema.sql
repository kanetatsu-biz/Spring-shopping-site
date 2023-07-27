-- 各種ビュー削除
DROP VIEW IF EXISTS v_order_histories;

-- 各種テーブル削除
DROP TABLE IF EXISTS order_details;
DROP TABLE IF EXISTS orders;
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
   category_id INTEGER,
   name TEXT,
   price INTEGER,
   description TEXT,
   FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- アカウントテーブル
CREATE TABLE accounts
(
   id SERIAL PRIMARY KEY,
   name TEXT,
   email TEXT UNIQUE,
   password TEXT
);

-- あて先テーブル
CREATE TABLE addresses
(
   id SERIAL PRIMARY KEY,
   post_num TEXT,
   prefecture TEXT,
   municipality TEXT,
   house_num TEXT,
   building_name_room_num TEXT
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
      COALESCE(a.name, 'ゲスト') AS acoount_name,
      to_char(o.ordered_datetime, 'YYYY-MM-DD HH24:MI:SS') AS ordered_datetime,
      o.total_price
   FROM orders o
   LEFT JOIN accounts a
      ON o.customer_id = a.id
);
