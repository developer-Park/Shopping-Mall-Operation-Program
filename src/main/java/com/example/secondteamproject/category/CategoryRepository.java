package com.example.secondteamproject.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);


    List<Category> findCategoriesByLayer(int layer);
    List<Category> findAllByOrderByNameAsc();

}