package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource
public interface CoinRepository extends JpaRepository<Coin, String> {
	// Prevents GET
	Coin findByCurrency(String currency);
	// Prevents POST
	@Transactional
	Coin save(Coin c);
	// Prevents DELETE
	@Transactional
	int deleteByCurrency(String currency);
}