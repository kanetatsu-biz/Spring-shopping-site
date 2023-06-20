# Spring-shopping-site

※[設計資料](https://drive.google.com/drive/folders/1ymE40u6TIUJ3l7Xovj8CqWwCYwrb8XFI?usp=sharing)はgoogle drive上にまとめました。

## 概要説明
<img width="488" alt="スクリーンショット 2023-06-18 16 55 23" src="https://github.com/kanetatsu-biz/Spring-shopping-site/assets/77796726/0ce2e183-06f0-42d6-b351-5bf5eb163079">

## 機能一覧
### 基本機能
#### 一般ユーザ
 - アカウントの新規登録、ログイン機能
 - 商品の一覧表示、カテゴリーでの検索
 - 全画面でアカウント名の表示（セッション）
 - カートに追加、削除
 - カートから注文
### 追加機能
#### 全般
 - デザインの適用
#### 一般ユーザー
 - ログインユーザーの注文履歴一覧、詳細表示、検索
 - 商品一覧の検索
 - ゲストログイン
 - 欲しいものリストの表示、編集、追加、削除
 - レビュー投稿機能
 - 購入後にポイントを付与、表示
 - 購入時にポイントを使えるようにする
 - ファーストビューのレイアウト検討、表示
 - 注文のあて先リストの作成
#### 管理ユーザー
 - 商品の一覧表示、追加、編集、削除
 - アカウント一覧
 - 注文履歴一覧、詳細表示
 - 注文履歴の検索
 - 顧客ごとの注文履歴の検索の追加

## 使用環境
### システム構成
<img width="486" alt="スクリーンショット 2023-06-18 16 59 22" src="https://github.com/kanetatsu-biz/Spring-shopping-site/assets/77796726/6ea9c5f9-0b9d-4f09-a3cf-e0fa7d6a0513">

### 使用ツール

対象|ツール名
----|---- 
IDE|Eclipse  
Viewテンプレート|Thymeleaf  
バージョン管理|Git  
DB管理ツール|pgAdmin4  

## デモ動画
##### 新規登録　＞　ログイン　＞　商品一覧　＞　商品詳細　＞　カート　＞　注文　＞　ログアウト　までの一連の動きをデモ
https://github.com/kanetatsu-biz/Spring-shopping-site/assets/123366737/d21090fa-0fa0-45c6-9e8a-538dc48cc3f9


## 環境構築やること

 - Eclipseのインストール
 - PostgreSQLのインストール（その時の最新版ver_15.3をインストール）

　　ー＞インストールしただけでは、PowerShellでアクセスできなかったので環境変数を編集

　　（参考：https://dattesar.com/powershell-psql/ ）
 - pgAdmin4のインストール（PostgreSQLインストール時に一緒にインストールできた）
 - Eclipseで新規プロジェクトの作成

　　ー＞Spring新規スターター・プロジェクトで、以下の依存関係を追加

　　　Spring Web / Thymeleaf / Spring Data JPA / PostgreSQL Driver / Spring Boot DevTools / Lombok
 - GitHubで新規リポジトリの作成
 - EcilpseとGitHubとの接続

　　ー＞SSH接続できるように設定し、GitHubからプロジェクトへクローン

　　（参考：https://itsakura.com/eclipse-github-clone-push ）
 - プロジェクト作成時に追加されたファイルらをリモートへPUSH

　　ー＞プロジェクトを右クリック「チームの共有」から進めていき、全ファイルをステージングし、メッセージを入力し、「コミットおよびプッシュ」

　　　ー＞リモートに反映されていればOK
 - このプロジェクト用のDBとユーザをPostgreSQLに用意する

　　ー＞powerShellにて、以下を実行

　　　1. 「psql -U postgres」で、デフォルトのpostgresにアクセス

　　　2. 下記のコマンドをそれぞれ実行（「admin」というユーザと「spring_shopping」というDBを作成）

　　　　　CREATE USER admin WITH PASSWORD 'admin';

　　　　　CREATE DATABASE spring_shopping OWNER admin ENCODING 'UTF8';

　　　3. 「\du」でユーザ、「\l」でプロジェクト用のDBが追加されていることを確認
 - プロジェクトとPostgreSQLを接続できるように設定
 
　　ー＞作成したプロジェクトの「src/main/resources/application.properties」に以下URL先の「application.properties」の記述を丸々コピペ

　　https://drive.google.com/drive/folders/1TsrKXWroa_q89CATuWDiTvkvErqUjfap?usp=sharing

　　　ー＞サーバーが問題なく起動できればOK

