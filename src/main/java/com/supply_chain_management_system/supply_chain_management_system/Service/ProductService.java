package com.supply_chain_management_system.supply_chain_management_system.Service;

import com.supply_chain_management_system.supply_chain_management_system.Model.Product;
import com.supply_chain_management_system.supply_chain_management_system.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }

    public Product updateProduct(Long id, Product product) {

        Product existingProduct = getProductById(id);

        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setUnit(product.getUnit());
        existingProduct.setStatus(product.getStatus());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }

        productRepository.deleteById(id);
    }

    // Pagination
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    // Search
    public Page<Product> searchProducts(
            String name,
            Pageable pageable) {

        return productRepository
                .findByProductNameContainingIgnoreCase(
                        name,
                        pageable
                );
    }

    // Advanced Filtering
    public Page<Product> filterProducts(
            String category,
            String status,
            Double minPrice,
            Double maxPrice,
            Pageable pageable) {

        return productRepository.filterProducts(
                category,
                status,
                minPrice,
                maxPrice,
                pageable
        );
    }

}
