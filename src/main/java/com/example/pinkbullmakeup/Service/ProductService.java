package com.example.pinkbullmakeup.Service;

import com.example.pinkbullmakeup.DTO.AddProduct;
import com.example.pinkbullmakeup.DTO.ProductResponseForCustomer;
import com.example.pinkbullmakeup.Entity.Brand;
import com.example.pinkbullmakeup.Entity.Category;
import com.example.pinkbullmakeup.Entity.OrderItem;
import com.example.pinkbullmakeup.Entity.Product;
import com.example.pinkbullmakeup.Exceptions.AlreadyExistsException;
import com.example.pinkbullmakeup.Exceptions.InsufficientStockException;
import com.example.pinkbullmakeup.Exceptions.ResourceNotFoundException;
import com.example.pinkbullmakeup.Mapping.ProductMapping;
import com.example.pinkbullmakeup.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapping productMapping;
    private final CategoryService categoryService;

    private final BrandService brandService;

    public ProductService(ProductRepository productRepository, ProductMapping productMapping, CategoryService categoryService, BrandService brandService) {
        this.productRepository = productRepository;
        this.productMapping = productMapping;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    public ProductResponseForCustomer addProduct(AddProduct addProduct){
        Product product = productMapping.toProduct(addProduct,categoryService,brandService);

        if (productRepository.existsByProductNameAndProductBrandAndProductCategory(
                product.getProductName(),
                product.getProductBrand(),
                product.getProductCategory())) {
            throw new AlreadyExistsException(product.getProductName());
        }

        Product newProduct = productRepository.save(product);
        return productMapping.toCustomerResponse(newProduct);

    }

    @Transactional(readOnly = true)
    public List<ProductResponseForCustomer> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return productMapping.toCustomerResponseList(products);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseForCustomer> getAllProductsByBrand(String brandName){
        Brand brand = brandService.findBrandByName(brandName);

        List<Product> products = productRepository.findAllByProductBrand(brand);

        return productMapping.toCustomerResponseList(products);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseForCustomer> getAllProductsByCategory(String categoryName){
        Category category = categoryService.findByName(categoryName);

        List<Product> products = productRepository.findAllByProductCategory(category);

        return productMapping.toCustomerResponseList(products);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseForCustomer> sortAllProductsByPriceLowToHigh(){
        List<Product> products = productRepository.findAllByOrderByProductPriceAsc();

        return productMapping.toCustomerResponseList(products);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseForCustomer> sortAllProductsByPriceHighToLow(){
        List<Product> products = productRepository.findAllByOrderByProductPriceDesc();

        return productMapping.toCustomerResponseList(products);
    }

    public void deleteProduct(UUID productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: " + productId + " not found."));


        product.getShades().clear();
        productRepository.save(product);
        productRepository.deleteById(productId);
    }


    public void updateProductPrice(float newPrice,UUID productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product with id: " + productId + " not found."));

        product.setProductPrice(newPrice);

        productRepository.save(product);
    }

    public void updateProductStock(int newQuantity, UUID productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product with id: " + productId + " not found."));

        product.setProductQuantityInStock(newQuantity);

        productRepository.save(product);
    }

    public void reduceStockAfterSale(List<OrderItem> orderItems) {
        // Assuming your product repository supports batch updates, this could be optimized further
        List<Product> updatedProducts = new ArrayList<>();

        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            int currentStock = product.getProductQuantityInStock();
            int quantityToReduce = item.getQuantity();

            // Check if enough stock is available
            if (currentStock < quantityToReduce) {
                throw new InsufficientStockException("Not enough stock for product: " + product.getProductId());
            }

            // Reduce stock
            product.setProductQuantityInStock(currentStock - quantityToReduce);

            // Add the updated product to the list
            updatedProducts.add(product);
        }

        // Save all updated products in one batch (assuming batch support)
        productRepository.saveAll(updatedProducts);
    }


    public Product findById(UUID productId){
        return productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product with id: " + productId + " not found."));
    }
}
