package com.example.secondteamproject.repository;


import com.example.secondteamproject.entity.Item;
import io.lettuce.core.ScanIterator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//findAllDesc 삭제
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByModifiedAtDesc();

<<<<<<< HEAD
    // ScanIterator<Object> findAllDesc();
=======
>>>>>>> 4db9a6af46ce67cb6c1f8a4a311c92aa893d2031
}
