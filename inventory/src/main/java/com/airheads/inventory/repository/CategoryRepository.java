package com.airheads.inventory.repository;

import com.airheads.inventory.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryId(Long categoryId);
}
