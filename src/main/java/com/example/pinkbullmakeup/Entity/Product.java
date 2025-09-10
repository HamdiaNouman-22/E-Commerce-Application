package com.example.pinkbullmakeup.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID productId;
    private String productName;

    @ManyToOne
    @JsonManagedReference
    private Category productCategory;

    @ManyToOne
    @JsonManagedReference
    private Brand productBrand;


    @Column(length = 512)
    private String productImage;


    private float productPrice;


    private int productQuantityInStock;

    @ElementCollection
    @CollectionTable(
            name = "product_shades",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @UniqueElements
    private List<Shade> shades = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    public Product() {}

    public Product( String productName, Category productCategory, Brand productBrand, String productImage, float productPrice, int productQuantityInStock, List<Shade> shades, List<Review> reviews) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productBrand = productBrand;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productQuantityInStock = productQuantityInStock;
        this.shades = shades;
        this.reviews = reviews;
    }

    public boolean searchShadeInProductList(String shadeName) {
        if (shadeName == null) {
            throw new IllegalArgumentException("Value should not be null.");
        }

        return shades.stream()
                .anyMatch(shade -> shade.getShadeName().equals(shadeName));
    }


    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantityInStock() {
        return productQuantityInStock;
    }

    public void setProductQuantityInStock(int productQuantityInStock) {
        this.productQuantityInStock = productQuantityInStock;
    }

    public Brand getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(Brand productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<Shade> getShades() {
        return shades;
    }

    public void setShades(List<Shade> shades) {
        this.shades = shades;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void updateProductStockAfterSale(int itemsSold){
        int currStock = this.productQuantityInStock;

        currStock -= itemsSold;

        this.productQuantityInStock = currStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productCategory=" + productCategory +
                ", productBrand=" + productBrand +
                ", productImage='" + productImage + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantityInStock=" + productQuantityInStock +
                ", shades=" + shades +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(productName, product.productName) &&
                Objects.equals(productBrand, product.productBrand) &&
                Objects.equals(productCategory, product.productCategory);
    }


}
