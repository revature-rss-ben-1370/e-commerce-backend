package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.services.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://2205java-sre-p3.s3-website-us-east-1.amazonaws.com/", allowCredentials = "true")
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getInventory(@RequestParam(required = false) boolean sale, @RequestParam(required = false) String query) {
         if(sale)
              return ResponseEntity.ok(productService.findSaleItems());
        if (query == null) {
          return ResponseEntity.ok(productService.findAll());
        }
  
        return ResponseEntity.ok(productService.searchProduct(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Authorized
    @PutMapping
    public ResponseEntity<Product> upsert(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @Authorized
    @PatchMapping
    public ResponseEntity<List<Product>> purchase(@RequestBody List<ProductInfo> metadata) { 	
    	List<Product> productList = new ArrayList<>();

        for (ProductInfo metadatum : metadata) {
            Optional<Product> optional = productService.findById(metadatum.getId());

            if (!optional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Product product = optional.get();

            if (product.getQuantity() - metadatum.getQuantity() < 0) {
                return ResponseEntity.badRequest().build();
            }

            product.setQuantity(product.getQuantity() - metadatum.getQuantity());
            productList.add(product);
        }
        
        productService.saveAll(productList, metadata);

        return ResponseEntity.ok(productList);
    }

    @Authorized
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        if(!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.delete(id);

        return ResponseEntity.ok(optional.get());
    }
}
