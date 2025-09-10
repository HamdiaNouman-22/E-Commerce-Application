package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.DTO.AddProduct;
import com.example.pinkbullmakeup.Entity.Shade;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductUploadService {

    private final ImageStorageService imageStorageService;

    public ProductUploadService(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    public AddProduct handleUpload(AddProduct addProduct, MultipartFile productImageFile, List<MultipartFile> shadeImageFiles) {
        String productImageUrl = imageStorageService.store(productImageFile);
        addProduct.setProdImage(productImageUrl);

        List<Shade> updatedShades = new ArrayList<>();
        for (int i = 0; i < addProduct.getShades().size(); i++) {
            Shade originalShade = addProduct.getShades().get(i);
            String shadeImageUrl = imageStorageService.store(shadeImageFiles.get(i));
            updatedShades.add(new Shade(originalShade.getShadeName(), shadeImageUrl));
        }

        addProduct.setShades(updatedShades);
        return addProduct;
    }
}

