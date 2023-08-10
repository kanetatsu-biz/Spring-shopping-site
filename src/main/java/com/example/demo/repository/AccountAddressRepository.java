package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AccountAddress;
import com.example.demo.entity.AccountAddressKey;

public interface AccountAddressRepository extends JpaRepository<AccountAddress, AccountAddressKey> {
	// SELECT * FROM account_addresses aa join addresses a on aa.address_id = a.id WHERE account_id = ?
	List<AccountAddress> findByAccountId(Integer accountId);
}
