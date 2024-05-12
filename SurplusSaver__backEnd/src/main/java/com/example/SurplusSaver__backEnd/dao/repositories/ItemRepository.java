package com.example.SurplusSaver__backEnd.dao.repositories;

import com.example.SurplusSaver__backEnd.dao.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
