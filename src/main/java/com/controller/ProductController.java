package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.ResponseDto;
import com.entity.ProductEntity;
import com.repository.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Product APIs", description = "Operations about products")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	// add new product
	@PostMapping("/products")
	public ResponseEntity<ProductEntity> addProduct(@RequestBody ProductEntity product) {
		// validation
		productRepository.save(product);

//		return ResponseEntity.ok(product);// 200

		return ResponseEntity.status(HttpStatus.CREATED).body(product); // 201
	}

	// get all products
	@GetMapping("/products")
	public List<ProductEntity> getAllProducts() {
		List<ProductEntity> products = productRepository.findAll();
		return products;
	}

	// remove product => id
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<?> removeProduct(@PathVariable UUID productId) {

		Optional<ProductEntity> op = productRepository.findById(productId);

		if (op.isEmpty()) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("productId", productId);
			map.put("msg", "Invalid ProductId");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} else {
			productRepository.deleteById(productId);
			return ResponseEntity.ok(op.get());
			//msg message 
		}
	}

	@Operation(summary = "Get product by id", description = "Returns product if exists")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
	// get single product
	@GetMapping("/products/{productId}")
	public ResponseEntity<ResponseDto<?>> getProductById(@PathVariable UUID productId) {
		Optional<ProductEntity> op = productRepository.findById(productId);

		ResponseDto<ProductEntity> map = new ResponseDto<>();

		if (op.isEmpty()) {
			map.setCode(-1);
			map.setData(null);
			map.setMsg("Invalid Key");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
		} else {
			map.setCode(9);
			map.setData(op.get());
			map.setMsg("Data reterived");

			return ResponseEntity.ok(map);
		}
	}

	// update product
	// qty
	// price

	@PutMapping("/products/{productId}")
	public ResponseEntity<?> updateProductById(@PathVariable UUID productId, @RequestBody ProductEntity product) {
		Optional<ProductEntity> op = productRepository.findById(productId);

		if (op.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productId);
		} else {
			ProductEntity dbProduct = op.get();
			dbProduct.setQty(product.getQty());
			dbProduct.setPrice(product.getPrice());
			productRepository.save(dbProduct);
			return ResponseEntity.ok(dbProduct);
		}
	}
}
