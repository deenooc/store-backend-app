package com.example.store.repository;

import com.example.store.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"orders"})
    Optional<Product> findWithOrdersById(Long id);

    @EntityGraph(attributePaths = {"orders"})
    Page<Product> findAll(Pageable pageable);
}
