package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AccountAddress;
import com.example.demo.entity.AccountAddressKey;

public interface AccountAddressRepository extends JpaRepository<AccountAddress, AccountAddressKey> {
	// SELECT * FROM account_addresses aa join addresses a on aa.address_id = a.id WHERE account_id = ?
	List<AccountAddress> findByAccountId(Integer accountId);

	//	SELECT EXISTS (SELECT 1 FROM account_addresses WHERE account_id = ? AND address_name = ?);
	boolean existsByAccountIdAndAddressName(Integer accountId, String addressName);

	// SELECT * FROM account_addresses aa join addresses a on aa.address_id = a.id
	// WHERE account_id = ? AND address_id = ?
	Optional<AccountAddress> findByAccountIdAndAddressId(Integer accountId, Integer addressId);
}
