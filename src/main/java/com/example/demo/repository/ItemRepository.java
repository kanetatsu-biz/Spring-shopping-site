package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	// SELECT * FROM items WHERE category_id = ? ORDER BY id
	List<Item> findByCategoryIdOrderById(Integer categoryId);

	//	商品名、価格の下限・上限をもとにクエリを作成
	@Query("SELECT i FROM Item i WHERE " +
			"(:name = '' OR i.name ILIKE :name)" +
			"AND (:minPrice IS NULL OR i.price >= :minPrice)" +
			"AND (:maxPrice IS NULL OR i.price <= :maxPrice)" +
			"ORDER BY i.id")
	List<Item> searchByCriteria(
			@Param("name") String name,
			@Param("minPrice") Integer minPrice,
			@Param("maxPrice") Integer maxPrice);

	//	SELECT EXISTS (SELECT 1 FROM item WHERE item_name = ? AND id != ?);
	boolean existsByNameAndIdNot(String name, Integer id);

	//	SELECT EXISTS (SELECT 1 FROM item WHERE item_name = ?);
	boolean existsByName(String name);
}
