package com.rahul.cartify.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rahul.cartify.service.ImageUploadService;

@Service
public class ImageUploadServiceImpl
        implements ImageUploadService {

    private final Cloudinary cloudinary;

    public ImageUploadServiceImpl(
            Cloudinary cloudinary
    ) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(
            MultipartFile file
    ) {

        try {

            Map uploadResult =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.emptyMap()
                    );

            return uploadResult
                    .get("secure_url")
                    .toString();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Image upload failed"
            );
        }
    }
}