package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Category;
import com.example.demo.entity.Item;
import com.example.demo.entity.WishList;
import com.example.demo.model.Cart;
import com.example.demo.model.LoginUser;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.WishListRepository;
import com.example.demo.service.SearchService;
import com.example.demo.service.validation.ItemValidationService;

@Controller
public class ItemController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	Cart cart;

	@Autowired
	LoginUser loginUser;

	@Autowired
	WishListRepository wishListRepository;

	// 商品一覧表示
	@GetMapping("/items")
	public String index(
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "itemName", defaultValue = "") String itemName,
			@RequestParam(value = "matchPattern", defaultValue = "partial") String matchPattern,
			@RequestParam(value = "minPrice", defaultValue = "") Integer minPrice,
			@RequestParam(value = "maxPrice", defaultValue = "") Integer maxPrice,
			Model model) {

		//	全カテゴリーを取得
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		List<Item> items = null;

		//	カテゴリーが指定されていたら他の検索条件は無視
		if (categoryId != null) {
			//	カテゴリーで絞った商品を取得
			items = itemRepository.findByCategoryIdAndIsDeletedFalseOrderById(categoryId);
		} else {
			//	入力値を文字列検索用に置き換え
			String itemNameCriteria = SearchService.getValueWithWildcard(itemName, matchPattern);

			//	指定された値をもとに検索した商品を取得
			items = itemRepository.searchByCriteriaIsDeletedFalse(
					itemNameCriteria, minPrice, maxPrice);
		}

		//すでにカートに入っている商品は在庫を変更
		for (Item cartItem : cart.getItems()) {
			for (Item item : items) {
				calcPurchasableStock(item, cartItem);
			}
		}

		// ほしい物リストに入っている商品のほしい物追加ボタンの表示変更
		// ほしい物リストを取得
		List<WishList> userWishItems = wishListRepository.findByCustomerId(loginUser.getId());
		for (WishList userWishItem : userWishItems) {
			// ほしい物リストに入っているかどうかを検索
			items.stream()
					.filter(item -> item.getId() == userWishItem.getItemId())
					.findFirst()
					.get()
					// ほしい物リストに入っている商品にはtrueを付与
					.setInWishList(true);
		}

		model.addAttribute("items", items);

		//	商品名検索のマッチパターンを取得し、画面に渡す
		model.addAttribute("matchPatterns", SearchService.stringMatchPatterns);

		//	検索条件を保持
		model.addAttribute("itemName", itemName);
		model.addAttribute("matchPattern", matchPattern);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);

		return "items";
	}

	// 商品詳細表示
	@GetMapping("/items/{itemId}")
	public String show(
			@PathVariable("itemId") Integer itemId,
			Model model) {

		//	商品IDをもとに商品を取得
		Item item = itemRepository.findById(itemId).get();

		//すでにカートに入っている商品は在庫を変更
		for (Item cartItem : cart.getItems()) {
			calcPurchasableStock(item, cartItem);
		}

		// ほしい物リストに入っているか検索
		item.setInWishList(wishListRepository.existsByCustomerIdAndItemId(loginUser.getId(), itemId));

		model.addAttribute("item", item);

		return "showItem";
	}

	public void calcPurchasableStock(Item item, Item cartItem) {

		//	カートに既に商品が存在している場合
		if (item.getId() == cartItem.getId()) {
			//	在庫情報をもとに購入可能な数量を計算しなおす
			item.setStock(item.getStock() - cartItem.getQuantity());
		}
	}

	// 【管理】商品一覧表示
	@GetMapping("/admin/items")
	public String adminIndex(
			@RequestParam(value = "categoryId", defaultValue = "") Integer categoryId,
			@RequestParam(value = "itemName", defaultValue = "") String itemName,
			@RequestParam(value = "matchPattern", defaultValue = "partial") String matchPattern,
			@RequestParam(value = "minPrice", defaultValue = "") Integer minPrice,
			@RequestParam(value = "maxPrice", defaultValue = "") Integer maxPrice,
			@RequestParam(name = "successMes", defaultValue = "") String successMes,
			Model model) {

		//	全カテゴリーを取得
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		List<Item> items = null;

		//	カテゴリーが指定されていたら他の検索条件は無視
		if (categoryId != null) {
			//	カテゴリーで絞った商品を取得
			items = itemRepository.findByCategoryIdOrderById(categoryId);
		} else {
			//	入力値を文字列検索用に置き換え
			String itemNameCriteria = SearchService.getValueWithWildcard(itemName, matchPattern);

			//	指定された値をもとに検索した商品を取得
			items = itemRepository.searchByCriteria(
					itemNameCriteria, minPrice, maxPrice);
		}

		//すでにカートに入っている商品は在庫を変更
		for (Item cartItem : cart.getItems()) {
			for (Item item : items) {
				calcPurchasableStock(item, cartItem);
			}
		}

		model.addAttribute("items", items);

		//	商品名検索のマッチパターンを取得し、画面に渡す
		model.addAttribute("matchPatterns", SearchService.stringMatchPatterns);

		//	検索条件を保持
		model.addAttribute("itemName", itemName);
		model.addAttribute("matchPattern", matchPattern);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);

		model.addAttribute("successMes", successMes);

		return "admin/items";
	}

	// 【管理】商品編集画面表示
	@GetMapping("/admin/items/{itemId}/edit")
	public String adminEditItem(
			@PathVariable("itemId") Integer itemId,
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			@ModelAttribute("inputItem") Item inputItem,
			Model model) {

		//	全カテゴリーを取得
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		//	商品IDをもとに商品を取得（エラーがある場合は前回の入力値を代入）
		Item item = !errMes.equals("") ? inputItem : itemRepository.findById(itemId).get();
		model.addAttribute("item", item);

		model.addAttribute("errMes", errMes);

		return "admin/editItem";
	}

	@Value("${upload.img.path}") // アップロード先ディレクトリのパスを環境変数から取得
	private String uploadImgPath;

	// 【管理】商品更新処理
	@PostMapping("/admin/items/{itemId}/update")
	@Transactional
	public String adminUpdateItem(
			@PathVariable("itemId") Integer itemId,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam("categoryId") Integer categoryId,
			@RequestParam(value = "price", defaultValue = "") Integer price,
			@RequestParam(value = "stock", defaultValue = "") Integer stock,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "fileName", defaultValue = "") String fileName,
			@RequestParam(value = "imgFile", defaultValue = "null") MultipartFile imgFile,
			RedirectAttributes redirectAttributes,
			Model model) {

		String errMes = ""; // エラーメッセージ
		Item inputItem = new Item(itemId, categoryId, name, price, description, fileName, stock); // 商品の入力値

		//	ファイル名を取得
		String imgFileName = imgFile.getOriginalFilename();

		//	１－１，必須のバリデーション
		if (ItemValidationService.validateRequiredFields(inputItem)) {
			errMes = "「商品名」「価格」「在庫数」は必須項目です。";
		}
		//	１－２，商品名の重複のバリデーション
		else if (itemRepository.existsByNameAndIdNot(name, itemId)) {
			errMes = "この商品名は既に他で登録済みです。";
		}
		//	１－３，画像ファイルの拡張子のバリデーション(png, jpg, jpegのみ可)
		if (ItemValidationService.validateFileExtension(imgFile, imgFileName)) {
			errMes = "ファイルの拡張子は「,png」「.jpg」「.jpeg」のいずれかを指定してください。";
		}

		//	商品IDをもとに商品を取得
		Item updateItem = itemRepository.findById(itemId).get();

		//	１－４，削除済みかのバリデーション
		if (updateItem.isDeleted()) {
			errMes = "この商品は既に削除されています。";
		}
		//	１－５，バリデーションにはじかれた場合
		if (!errMes.equals("")) {
			//	入力値とエラーメッセージをリダイレクト先に送る
			redirectAttributes.addFlashAttribute("inputItem", inputItem);
			redirectAttributes.addAttribute("errMes", errMes);
			return "redirect:/admin/items/" + itemId + "/edit";
		}

		//	情報を更新
		updateItem.setName(name);
		updateItem.setCategoryId(categoryId);
		updateItem.setPrice(price);
		updateItem.setStock(stock);
		updateItem.setDescription(description);
		//	画像ファイルが指定されていない場合は変更しない
		if (!imgFile.isEmpty()) {
			updateItem.setFileName(imgFileName);
		}

		try {
			//	DBに保存
			itemRepository.save(updateItem);

			//	画像ファイルが指定されていた場合
			if (!imgFile.isEmpty()) {
				//	指定された画像ファイルと同じファイル名と、絶対パスを設定
				Path targetFile = Paths.get(this.uploadImgPath).resolve(
						Paths.get(imgFileName)).normalize().toAbsolutePath();

				//	画像ファイルの中身をコピー
				try (InputStream inputStream = imgFile.getInputStream()) {
					//	対象のファイルを作成しそこに書き込み（既存のファイルがある場合は上書き）
					Files.copy(inputStream, targetFile,
							StandardCopyOption.REPLACE_EXISTING);

				} catch (IOException e) {
					//	画像ファイルのアップロードがうまくいかなかった場合
					e.printStackTrace();
					// エラーメッセージを渡してリダイレクト
					redirectAttributes.addAttribute("errMes", "画像ファイルが保存できませんでした。");
					return "redirect:/admin/items/" + itemId + "/edit";
				}
			}
		} catch (Exception e) {
			//	DBの更新がうまくいかなかった場合
			e.printStackTrace();
			// エラーメッセージを渡してリダイレクト
			redirectAttributes.addAttribute("errMes", "商品の更新ができませんでした。");
			return "redirect:/admin/items/" + itemId + "/edit";
		}

		//	更新が完了した旨を一覧画面に送る
		redirectAttributes.addAttribute("successMes", "商品の更新が完了しました。");

		return "redirect:/admin/items";
	}

	// 【管理】商品追加画面表示
	@GetMapping("/admin/items/add")
	public String adminAddItem(
			@RequestParam(name = "errMes", defaultValue = "") String errMes,
			@ModelAttribute("inputItem") Item inputItem,
			Model model) {

		//	全カテゴリーを取得
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		model.addAttribute("errMes", errMes);
		model.addAttribute("item", inputItem == null ? new Item() : inputItem);

		return "admin/addItem";
	}

	// 【管理】商品新規登録処理
	@PostMapping("/admin/items/create")
	@Transactional
	public String adminCreateItem(
			@RequestParam(value = "imgFile") MultipartFile imgFile,
			@ModelAttribute Item inputItem, // 入力値をそのままオブジェクトに詰める
			RedirectAttributes redirectAttributes,
			Model model) {

		String errMes = ""; // エラーメッセージ

		//	ファイル名を取得
		String imgFileName = imgFile.getOriginalFilename();

		//	１－１，必須のバリデーション
		if (ItemValidationService.validateRequiredFields(inputItem)) {
			errMes = "「商品名」「価格」「在庫数」は必須項目です。";
		}
		//	１－２，商品名の重複のバリデーション
		else if (itemRepository.existsByName(inputItem.getName())) {
			errMes = "この商品名は既に他で登録済みです。";
		}
		//	１－３，画像ファイルの拡張子のバリデーション(png, jpg, jpegのみ可)
		if (ItemValidationService.validateFileExtension(imgFile, imgFileName)) {
			errMes = "ファイルの拡張子は「,png」「.jpg」「.jpeg」のいずれかを指定してください。";
		}
		//	１－４，バリデーションにはじかれた場合
		if (!errMes.equals("")) {
			//	入力値とエラーメッセージをリダイレクト先に送る
			redirectAttributes.addFlashAttribute("inputItem", inputItem);
			redirectAttributes.addAttribute("errMes", errMes);
			return "redirect:/admin/items/add";
		}

		//	画像ファイルが指定されていた場合はファイル名をセット
		if (!imgFile.isEmpty()) {
			inputItem.setFileName(imgFileName);
		}

		try {
			//	DBに保存
			itemRepository.save(inputItem);

			//	画像ファイルが指定されていた場合
			if (!imgFile.isEmpty()) {
				//	指定された画像ファイルと同じファイル名と、絶対パスを設定
				Path targetFile = Paths.get(this.uploadImgPath).resolve(
						Paths.get(imgFileName)).normalize().toAbsolutePath();

				//	画像ファイルの中身をコピー
				try (InputStream inputStream = imgFile.getInputStream()) {
					//	対象のファイルを作成しそこに書き込み（既存のファイルがある場合は上書き）
					Files.copy(inputStream, targetFile,
							StandardCopyOption.REPLACE_EXISTING);

				} catch (IOException e) {
					//	画像ファイルのアップロードがうまくいかなかった場合
					e.printStackTrace();
					// エラーメッセージを渡してリダイレクト
					redirectAttributes.addAttribute("errMes", "画像ファイルが保存できませんでした。");
					return "redirect:/admin/items/add";
				}
			}
		} catch (Exception e) {
			//	DBの登録がうまくいかなかった場合
			e.printStackTrace();
			// エラーメッセージを渡してリダイレクト
			redirectAttributes.addAttribute("errMes", "商品の新規登録ができませんでした。");
			return "redirect:/admin/items/add";
		}

		//	登録が完了した旨を一覧画面に送る
		redirectAttributes.addAttribute("successMes", "商品の新規登録が完了しました。");

		return "redirect:/admin/items";
	}

	// 【管理】商品削除処理
	@PostMapping("/admin/items/{itemId}/delete")
	public String adminDeleteItem(
			@PathVariable("itemId") Integer itemId,
			RedirectAttributes redirectAttributes) {

		//	論理削除処理（データは消さないが消したことにする）
		//	※注文履歴で参照されるため
		Item softDeleteItem = itemRepository.findById(itemId).get();
		softDeleteItem.setDeleted(true);
		itemRepository.save(softDeleteItem);

		//	削除が完了した旨を一覧画面に送る
		redirectAttributes.addAttribute("successMes", "商品の削除が完了しました。");

		return "redirect:/admin/items";
	}
}
