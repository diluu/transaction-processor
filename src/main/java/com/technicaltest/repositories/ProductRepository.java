package com.technicaltest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.technicaltest.objects.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
