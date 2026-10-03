package com.supply_chain_management_system.supply_chain_management_system.Controller;

import com.supply_chain_management_system.supply_chain_management_system.DTO.ProductDTO;
import com.supply_chain_management_system.supply_chain_management_system.Model.Product;
import com.supply_chain_management_system.supply_chain_management_system.Service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(
            ProductService productService,
            ModelMapper modelMapper) {

        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody ProductDTO dto) {

        Product product =
                modelMapper.map(dto, Product.class);

        Product saved =
                productService.saveProduct(product);

        return ResponseEntity.ok(
                modelMapper.map(
                        saved,
                        ProductDTO.class
                )
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {

        List<ProductDTO> products =
                productService.getAllProducts()
                        .stream()
                        .map(product ->
                                modelMapper.map(
                                        product,
                                        ProductDTO.class
                                )
                        )
                        .toList();

        return ResponseEntity.ok(products);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @PathVariable Long id) {

        Product product =
                productService.getProductById(id);

        return ResponseEntity.ok(
                modelMapper.map(
                        product,
                        ProductDTO.class
                )
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto) {

        Product product =
                modelMapper.map(dto, Product.class);

        Product updated =
                productService.updateProduct(
                        id,
                        product
                );

        return ResponseEntity.ok(
                modelMapper.map(
                        updated,
                        ProductDTO.class
                )
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                "Product deleted successfully"
        );
    }

    // PAGINATION + SORTING
    @GetMapping("/page")
    public ResponseEntity<Page<ProductDTO>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        Page<ProductDTO> products =
                productService
                        .getProducts(pageable)
                        .map(product ->
                                modelMapper.map(
                                        product,
                                        ProductDTO.class
                                )
                        );

        return ResponseEntity.ok(products);
    }

    // SEARCH + PAGINATION + SORTING
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        Page<ProductDTO> products =
                productService
                        .searchProducts(
                                name,
                                pageable
                        )
                        .map(product ->
                                modelMapper.map(
                                        product,
                                        ProductDTO.class
                                )
                        );

        return ResponseEntity.ok(products);
    }

    // ADVANCED FILTERING
    @GetMapping("/filter")
    public ResponseEntity<Page<ProductDTO>> filterProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        Page<ProductDTO> products =
                productService
                        .filterProducts(
                                category,
                                status,
                                minPrice,
                                maxPrice,
                                pageable
                        )
                        .map(product ->
                                modelMapper.map(
                                        product,
                                        ProductDTO.class
                                )
                        );

        return ResponseEntity.ok(products);
    }
}
