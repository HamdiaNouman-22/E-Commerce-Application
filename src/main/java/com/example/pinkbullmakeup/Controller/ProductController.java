package com.example.pinkbullmakeup.Controller;

import com.example.pinkbullmakeup.DTO.AddProduct;
import com.example.pinkbullmakeup.DTO.ProductResponseForCustomer;
import com.example.pinkbullmakeup.Entity.Product;
import com.example.pinkbullmakeup.Service.ProductService;
import com.example.pinkbullmakeup.Service.ProductUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Controller", description = "APIs for managing and retrieving products")
public class ProductController {

    private final ProductService productService;
    private final ProductUploadService productUploadService;

    public ProductController(ProductService productService, ProductUploadService productUploadService) {
        this.productService = productService;
        this.productUploadService = productUploadService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Add a new product",
            description = "Allows Admin to add a new product along with product images",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = AddProduct.class))
            )
    )
    public ResponseEntity<ProductResponseForCustomer> addProduct(
            @RequestPart("data") @Valid AddProduct addProduct,
            @RequestPart("image") MultipartFile imageFile,
            @RequestPart("shadeImages") List<MultipartFile> shadeImageFiles) {

        AddProduct populatedProduct = productUploadService.handleUpload(addProduct, imageFile, shadeImageFiles);
        ProductResponseForCustomer savedProduct = productService.addProduct(populatedProduct);

        return ResponseEntity
                .created(URI.create("/api/products/" + savedProduct.getProductId()))
                .body(savedProduct);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all available products")
    public ResponseEntity<List<ProductResponseForCustomer>> getAllProducts() {
        List<ProductResponseForCustomer> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/brand")
    @Operation(summary = "Get products by brand", description = "Retrieves all products belonging to a specific brand")
    public ResponseEntity<List<ProductResponseForCustomer>> getProductsByBrand(@RequestParam String brandName) {
        List<ProductResponseForCustomer> products = productService.getAllProductsByBrand(brandName);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/category")
    @Operation(summary = "Get products by category", description = "Retrieves all products belonging to a specific category")
    public ResponseEntity<List<ProductResponseForCustomer>> getProductsByCategory(@RequestParam String categoryName) {
        List<ProductResponseForCustomer> products = productService.getAllProductsByCategory(categoryName);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/sort/price-low-to-high")
    @Operation(summary = "Sort products by price ascending", description = "Retrieves products sorted from low to high price")
    public ResponseEntity<List<ProductResponseForCustomer>> sortProductsByPriceLowToHigh() {
        List<ProductResponseForCustomer> products = productService.sortAllProductsByPriceLowToHigh();
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/sort/price-high-to-low")
    @Operation(summary = "Sort products by price descending", description = "Retrieves products sorted from high to low price")
    public ResponseEntity<List<ProductResponseForCustomer>> sortProductsByPriceHighToLow() {
        List<ProductResponseForCustomer> products = productService.sortAllProductsByPriceHighToLow();
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by ID. Admin access only.")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/price")
    @Operation(summary = "Update product price", description = "Updates the price of a product by ID. Admin access only.")
    public ResponseEntity<Void> updateProductPrice(@RequestParam float newPrice, @PathVariable UUID id) {
        productService.updateProductPrice(newPrice, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock", description = "Updates the stock quantity of a product by ID. Admin access only.")
    public ResponseEntity<Void> updateProductStock(@RequestParam int newQuantity, @PathVariable UUID id) {
        productService.updateProductStock(newQuantity, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a single product by its ID")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }
}
