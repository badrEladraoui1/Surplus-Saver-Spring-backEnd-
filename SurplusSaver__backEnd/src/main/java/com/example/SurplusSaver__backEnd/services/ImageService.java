package com.example.SurplusSaver__backEnd.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImage(MultipartFile image);
    String getFileExtension(String fileName);

}
