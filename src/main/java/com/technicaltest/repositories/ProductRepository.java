package com.technicaltest.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.technicaltest.objects.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> findByExchangeCodeAndExpirationDateAndProductGroupCodeAndSymbol(String exchangeCode,
			LocalDate expriationDate, String productGroupCode, String symbol);

}
