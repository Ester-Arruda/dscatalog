package com.devsuperior.dscatalog.tests;

import java.time.Instant;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

public class Factory {
	
	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Lorem ipsum", 800.0, "bla", Instant.parse("2020-07-13T20:50:07Z"));
		product.getCategories().add(new Category(2L, "Electronic"));
		return product;
	}
	
	public static ProductDTO CreateProductDto() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
}
