# Spring-shopping-site
**Spring Shopping**というショッピングサイトを**Spring Boot**を用いて開発しました。

※[設計資料](https://drive.google.com/drive/folders/1ymE40u6TIUJ3l7Xovj8CqWwCYwrb8XFI?usp=sharing)は**Google ドライブ**上にまとめています。

## アピールポイント
 - Javaでの開発は初めての[kanetatsu-biz](https://github.com/kanetatsu-biz)と[kakeru2960](https://github.com/kakeru2960)の2人が協力して開発しました。
 - ユーザーが使いやすいようなシンプルなUIを意識しつつ、ユーザーが使いたいと思えるような機能の設計にも力を入れました。
 - 単純なCRUD処理の実装に限らず、バリデーションエラーの処理やアクセス制御など、セキュリティに重要な要素を実装しました。
 - 定期的なコードレビューやSlackでのコミュニケーションを通して、バグの発生を最小限に抑えました。
 - 開発スケジュール、DB設計、APIマッピングなど、変更が多い資料に関しては、柔軟にアップデートし、システムの保守性を確保しました。
 - 再利用可能な共通部品の開発に注力し、可用性を高めるための工夫をしました。

## システムのテーマ
<p align="center">
<img width="488" alt="システムのテーマ" src="https://github.com/kanetatsu-biz/Spring-shopping-site/assets/77796726/0ce2e183-06f0-42d6-b351-5bf5eb163079">
</p>

## デモ動画（2023/09/04時点）
https://github.com/kanetatsu-biz/Spring-shopping-site/assets/123366737/d3126bb7-c50f-48ba-bbfc-7f186ac48507

※GitHubへのアップロードの都合上、画質が粗くなっております。オリジナルやそれぞれの機能ごとのデモ動画は[こちら](https://drive.google.com/drive/folders/1ssPcmUMliLz-Szi0fN12-vsT1NiY9n2N?usp=sharing)をご確認ください。

## 使用環境
### システム構成
<p align="center">
<img width="486" alt="システム構成" src="https://github.com/kanetatsu-biz/Spring-shopping-site/assets/77796726/6ea9c5f9-0b9d-4f09-a3cf-e0fa7d6a0513">
</p>

### 使用ツール
<div align="center">

対象|ツール名
----|---- 
IDE|Eclipse  
Viewテンプレート|Thymeleaf  
バージョン管理|Git  
DB管理ツール|pgAdmin4  
コミュニケーションツール|Slack  
設計資料等の管理|Google ドライブ  

</div>


## 環境構築手順

1. Eclipseのインストール
2. PostgreSQLのインストール（必要であれば**pgAdmin4**も同時にインストール）
3. プロジェクト用のDBの作成
4. Eclipseで新規プロジェクトの作成<br>
ー＞Spring新規スターター・プロジェクトで、対象の依存関係(Spring Web / Thymeleaf / Spring Data JPA / PostgreSQL Driver / Spring Boot DevTools / Lombok)を追加
6. Ecilpse上でGitHubと連携できるようにし、このプロジェクトをクローンする
7. **application.properties**の中身は自身の環境に合わせて変更（この時に3で作成したDBと紐づける）
8. サーバーが問題なく起動できれば準備完了

## 今後の展望
 - 今回は学習目的で開発しましたが、次回はプロジェクトを本番環境にデプロイしたいと考えています。また、ログ出力やDBのバックアップ等に関しては今回触れていないため、特にセキュリティ、運用、保守などに焦点を当てた設計を心がけます。
 - フロントエンドに関しては、フレームワークを使用し、コンポーネントの共通化を実現する予定です。また、トレンドであるCSSフレームワーク「Tailwind CSS」や「SCSS」を導入し、開発を効率化していきたいです。
 - テストコードの導入や、マージ前のテストの実施の自動化など、品質保証に関する取り組みを行っていきたいです。
