package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.DTO.BrandResponse;
import com.example.pinkbullmakeup.Service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
@Tag(name = "Brand Controller", description = "APIs for managing brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a Brand", description = "Adds a new brand. Admin access only.")
    public ResponseEntity<BrandResponse> createBrand(@Valid @RequestParam String brandName) {
        BrandResponse brand = brandService.addBrand(brandName);
        return ResponseEntity.created(URI.create("/api/brands/" + brand.getBrandId())).body(brand);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update a Brand", description = "Updates the name of an existing brand. Admin access only.")
    public ResponseEntity<BrandResponse> updateBrand(@Valid @RequestParam String newBrandName, @PathVariable UUID id) {
        BrandResponse updatedBrand = brandService.updateBrand(newBrandName, id);
        return ResponseEntity.ok(updatedBrand);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Brand", description = "Deletes a brand by its ID. Admin access only.")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get All Brands", description = "Returns a list of all brands")
    public ResponseEntity<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> brands = brandService.getAllBrands();
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/search")
    @Operation(summary = "Get Brand by Name", description = "Finds a brand by its name")
    public ResponseEntity<BrandResponse> getBrandByName(@RequestParam String brandName) {
        BrandResponse brand = brandService.findByName(brandName);
        return ResponseEntity.ok(brand);
    }
}
