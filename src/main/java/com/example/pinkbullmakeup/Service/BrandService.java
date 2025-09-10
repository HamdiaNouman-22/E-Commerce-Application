package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.DTO.BrandResponse;
import com.example.pinkbullmakeup.Entity.Brand;
import com.example.pinkbullmakeup.Exceptions.AlreadyExistsException;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public BrandResponse brandToBrandResponse(Brand brand){
        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setBrandId(brand.getBrandId().toString());
        brandResponse.setBrandName(brand.getBrandName());

        return brandResponse;
    }

    public List<BrandResponse> brandListtoBrandResponseList(List<Brand> brands){
        List<BrandResponse> brandResponses = new ArrayList<>();

        for (Brand brand : brands){
            brandResponses.add(brandToBrandResponse(brand));
        }

        return brandResponses;
    }

    public BrandResponse addBrand(String brandName) {
        if (brandName == null)
            throw new IllegalArgumentException("Name should not be null");

        String name = brandName.toLowerCase().trim();

        if (brandRepository.existsByBrandName(name)){
            throw new AlreadyExistsException(name);
        }

        Brand brand = new Brand();
        brand.setBrandName(name);

        return brandToBrandResponse(brandRepository.save(brand));

    }

    public BrandResponse updateBrand(String newBrandName, UUID brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with id " + brandId + " not found."));


        Optional<Brand> existing = brandRepository.findBrandByBrandName(newBrandName);
        if (existing.isPresent() && !existing.get().getBrandId().equals(brandId)) {
            throw new AlreadyExistsException("Brand name '" + newBrandName);
        }

        brand.setBrandName(newBrandName);
        return brandToBrandResponse(brandRepository.save(brand));
    }


    public void deleteBrand(UUID brandId){
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ResourceNotFoundException("Brand with id " + brandId + " not found."));

        brandRepository.delete(brand);
    }

    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands(){
        return brandListtoBrandResponseList(brandRepository.findAll());
    }

    @Transactional(readOnly = true)
    public BrandResponse findByName(String brandName) {
        return brandToBrandResponse(brandRepository.findBrandByBrandName(brandName.toLowerCase().trim())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found.")));
    }

    @Transactional(readOnly = true)
    public Brand findBrandByName(String brandName) {
        return brandRepository.findBrandByBrandName(brandName.toLowerCase().trim())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found."));
    }

}
