package com.example.SurplusSaver__backEnd.controllers;

import com.example.SurplusSaver__backEnd.dao.entities.Item;
import com.example.SurplusSaver__backEnd.dao.entities.User;
import com.example.SurplusSaver__backEnd.services.ItemService;
import com.example.SurplusSaver__backEnd.security.JwtTokenProvider;
import com.example.SurplusSaver__backEnd.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/SurplusSaverApiV1/items")
public class ItemController {

    private final ItemService itemService;
    private final JwtTokenProvider jwtTokenProvider;
    UserService userService;

    public ItemController(ItemService itemService, JwtTokenProvider jwtTokenProvider , UserService userService) {
        this.itemService = itemService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<?> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//        String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
//        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
//    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<?> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file, @RequestParam("itemId") Long itemId) {
//        User user = userService.getUserById(token);
//        if(user == null){
//            return ResponseEntity.notFound().build();
//        }
//        String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
//
//        // Retrieve the Item entity
//        Item item = itemService.getItemById(itemId);
//        if (item == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Set the urlPicture attribute with the URL of the stored image
//        item.setItemPictureUrl(fileUrl);
//
//        // Save the updated Item entity
//        itemService.saveItem(item);
//
//        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
//    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<?> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("file") MultipartFile file, @RequestParam("itemId") Long itemId) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//        String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
//
//        // Retrieve the Item entity
//        Item item = itemService.getItemById(itemId);
//        if (item == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Set the urlPicture attribute with the URL of the stored image
//        item.setItemPictureUrl(fileUrl);
//
//        // Save the updated Item entity
//        itemService.saveItem(item);
//
//        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
//    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<?> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("files") List<MultipartFile> files, @RequestParam("itemIds") List<Long> itemIds) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        if (files.size() != itemIds.size()) {
//            return ResponseEntity.badRequest().body("The number of files and item IDs must match.");
//        }
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            Long itemId = itemIds.get(i);
//
//            String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
//
//            // Retrieve the Item entity
//            Item item = itemService.getItemById(itemId);
//            if (item == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            // Set the urlPicture attribute with the URL of the stored image
//            item.setItemPictureUrl(fileUrl);
//
//            // Save the updated Item entity
//            itemService.saveItem(item);
//        }
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<List<String>> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("files") MultipartFile[] files, @RequestParam("itemIds") Long[] itemIds) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        if (files.size() != itemIds.size()) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        List<String> fileUrls = new ArrayList<>();
//
//        for (int i = 0; i < files.size(); i++) {
//            MultipartFile file = files.get(i);
//            Long itemId = itemIds.get(i);
//
//            String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
//            fileUrls.add(fileUrl);
//
//            // Retrieve the Item entity
//            Item item = itemService.getItemById(itemId);
//            if (item == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            // Set the urlPicture attribute with the URL of the stored image
//            item.setItemPictureUrl(fileUrl);
//
//            // Save the updated Item entity
//            itemService.saveItem(item);
//        }
//
//        return new ResponseEntity<>(fileUrls, HttpStatus.OK);
//    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<List<String>> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("files") MultipartFile[] files, @RequestParam("itemIds") Long[] itemIds) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        if (files.length != itemIds.length) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        List<String> fileUrls = new ArrayList<>();
//
//        for (int i = 0; i < files.length; i++) {
//            MultipartFile file = files[i];
//            Long itemId = itemIds[i];
//
//            String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
//            fileUrls.add(fileUrl);
//
//            // Retrieve the Item entity
//            Item item = itemService.getItemById(itemId);
//            if (item == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            // Set the urlPicture attribute with the URL of the stored image
//            item.setItemPictureUrl(fileUrl);
//
//            // Save the updated Item entity
//            itemService.saveItem(item);
//        }
//
//        return new ResponseEntity<>(fileUrls, HttpStatus.OK);
//    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<List<String>> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("files") MultipartFile[] files, @RequestParam("itemIds") Long[] itemIds) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        if (files.length != itemIds.length || files.length > 3) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        List<String> fileUrls = new ArrayList<>();
//
//        for (int i = 0; i < files.length; i++) {
//            MultipartFile file = files[i];
//            Long itemId = itemIds[i];
//
//            String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
//            fileUrls.add(fileUrl);
//
//            // Retrieve the Item entity
//            Item item = itemService.getItemById(itemId);
//            if (item == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            // Set the urlPicture attribute with the URL of the stored image
//            item.setItemPictureUrl(fileUrl);
//
//            // Save the updated Item entity
//            itemService.saveItem(item);
//        }
//
//        return new ResponseEntity<>(fileUrls, HttpStatus.OK);
//    }

//    @PostMapping("/uploadImage")
//    public ResponseEntity<List<String>> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("files") MultipartFile[] files, @RequestParam("itemIds") Long[] itemIds) {
//        String jwt = token.substring(7); // Remove the "Bearer " prefix
//        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
//
//        if (files.length != itemIds.length || files.length > 3) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        List<String> fileUrls = new ArrayList<>();
//
//        for (int i = 0; i < files.length; i++) {
//            MultipartFile file = files[i];
//            Long itemId = itemIds[i];
//
//            String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
//            fileUrls.add(fileUrl);
//
//            // Retrieve the Item entity if it exists, or create a new one if it doesn't
//            Item item = itemId != null ? itemService.getItemById(itemId) : new Item();
//
//            if (item == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            // Set the urlPicture attribute with the URL of the stored image
//            item.setItemPictureUrl(fileUrl);
//
//            // Save the updated Item entity
//            itemService.saveItem(item);
//        }
//
//        return new ResponseEntity<>(fileUrls, HttpStatus.OK);
//    }

    @PostMapping("/uploadImage")
    public ResponseEntity<List<String>> uploadImage(@RequestHeader("Authorization") String token, @RequestParam("files") MultipartFile[] files) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);

        if (files.length > 3) {
            return ResponseEntity.badRequest().body(null);
        }

        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileUrl = itemService.saveItemImage(file); // This method should store the file and return its URL
            fileUrls.add(fileUrl);
        }

        return new ResponseEntity<>(fileUrls, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        Item item = itemService.getItemById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @GetMapping
    public ResponseEntity<?> getAllItems(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7); // Remove the "Bearer " prefix
        Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }
}