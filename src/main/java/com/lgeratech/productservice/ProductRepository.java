package com.lgeratech.productservice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer>{
	List<Product> findAll();
}
