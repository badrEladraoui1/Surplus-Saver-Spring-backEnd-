package com.example.SurplusSaver__backEnd.services;

import com.example.SurplusSaver__backEnd.dao.entities.Item;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {
    String saveItemImage(MultipartFile image);

    String getFileExtension(String fileName);

    Item getItemById(Long id);

    List<Item> getAllItems();

    void saveItem(Item item);
}
