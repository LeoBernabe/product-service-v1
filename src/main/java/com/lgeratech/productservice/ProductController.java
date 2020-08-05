package com.lgeratech.productservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProductController {	
	@Autowired
	ProductRepository productRepository;
	
	@PostMapping(value = "/createProduct")
	public ResponseEntity<String> createProduct(@RequestBody Product Product){
		productRepository.save(Product);
		return new ResponseEntity<String>("Product has been added succesfully", HttpStatus.OK);
	}
	
	@GetMapping(value = "/retrieveProducts")
	public ResponseEntity<List<Product>> retrieveProductList(){
		List<Product> Products = productRepository.findAll();
		return new ResponseEntity<List<Product>>(Products, HttpStatus.OK);
	}
	
	@GetMapping(value = "/Product/{productId}")
	public ResponseEntity<Product> getProducts(@PathVariable("productId") Integer productId){
		Optional<Product> product = productRepository.findById(productId);
		return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateProduct")
	public ResponseEntity<String> updateProduct(@RequestBody Product ProductRequest){
		Optional<Product> product = productRepository.findById(ProductRequest.getProductId());
		if(product.isPresent()) {
			product.get().setProductName(ProductRequest.getProductName());
			product.get().setProductDescription(ProductRequest.getProductDescription());
			product.get().setProductPrice(ProductRequest.getProductPrice());
			productRepository.save(product.get());
			return new ResponseEntity<String>("Product has been updated succesfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Can't Find Product by id = " + ProductRequest.getProductId(), HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping(value = "/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
		Optional<Product> Product = productRepository.findById(id);
		if(Product.isPresent()) {
			productRepository.delete(Product.get());
			return new ResponseEntity<String>("Product has been deleted succesfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Can't Find Product by id = " +  id, HttpStatus.BAD_REQUEST);
	}
}