package com.example.SurplusSaver__backEnd.services.Impl;

import com.example.SurplusSaver__backEnd.dao.entities.Item;
import com.example.SurplusSaver__backEnd.dao.repositories.ItemRepository;
import com.example.SurplusSaver__backEnd.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    private final Path root = Paths.get("itemImages");

    @Override
    public String saveItemImage(MultipartFile image) {
        try {
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }

            String extension = getFileExtension(image.getOriginalFilename());
            String newFileName = UUID.randomUUID().toString() + "." + extension;

            Path imagePath = root.resolve(newFileName);
            Files.copy(image.getInputStream(), imagePath);

            return imagePath.toString(); // return the full path
        } catch (IOException e) {
            throw new RuntimeException("Could not store the image. Error: " + e.getMessage());
        }
    }

    @Override
    public String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }
}