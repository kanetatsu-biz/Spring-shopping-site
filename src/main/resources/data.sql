INSERT INTO categories(name) VALUES('本');
INSERT INTO categories(name) VALUES('DVD');
INSERT INTO categories(name) VALUES('ゲーム');

INSERT INTO items(category_id, name, price) VALUES(1, '本１', 2500);
INSERT INTO items(category_id, name, price) VALUES(1, '本２　中間くらいの長さ', 980);
INSERT INTO items(category_id, name, price) VALUES(1, '本３　結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです', 1200);

INSERT INTO items(category_id, name, price) VALUES(2, 'DVD１', 2000);
INSERT INTO items(category_id, name, price) VALUES(2, 'DVD２　中間くらいの長さ', 1000);
INSERT INTO items(category_id, name, price) VALUES(2, 'DVD３　結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです', 1800);

INSERT INTO items(category_id, name, price) VALUES(3, 'ゲーム１', 780);
INSERT INTO items(category_id, name, price) VALUES(3, 'ゲーム２　中間くらいの長さ', 3400);
INSERT INTO items(category_id, name, price) VALUES(3, 'ゲーム３　結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです結構長めのタイトルです', 2200);

INSERT INTO accounts(name, email, password) VALUES('test1', 'test1@example.com', 'password');
INSERT INTO accounts(name, email, password) VALUES('test2', 'test2@example.com', 'password');