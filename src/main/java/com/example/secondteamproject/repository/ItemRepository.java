package com.example.secondteamproject.repository;


import com.example.secondteamproject.entity.Item;
import io.lettuce.core.ScanIterator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByModifiedAtDesc();

    // ScanIterator<Object> findAllDesc();
}
