package com.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entity.ProductEntity;
import com.repository.ProductRepository;

@RestController
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productId);
		} else {
			productRepository.deleteById(productId);
			return ResponseEntity.ok(op.get());
		}
	}

	// get single product

	@GetMapping("/products/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable UUID productId) {
		Optional<ProductEntity> op = productRepository.findById(productId);

		if (op.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productId);
		} else {
			return ResponseEntity.ok(op.get());
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
