package com.bestseller.coffee.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bestseller.coffee.store.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByIdAndIsDeleted(Long id, String stringNo);

	Optional<Product> findByNameIgnoreCase(String name);

}
