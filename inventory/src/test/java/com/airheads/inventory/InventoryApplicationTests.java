package com.airheads.inventory;

import com.airheads.inventory.dto.CategoryDto;
import com.airheads.inventory.dto.ProductDto;
import com.airheads.inventory.entity.Category;
import com.airheads.inventory.entity.Product;
import com.airheads.inventory.repository.CategoryRepository;
import com.airheads.inventory.repository.ProductRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@ActiveProfiles("test")
@SpringBootTest(
		classes = InventoryApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class InventoryApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private Integer port;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@AfterEach
	void tearDown() {
		categoryRepository.deleteAll();
		productRepository.deleteAll();
	}
	@Test
	void get_all_categories() throws Exception{
		Category category= new Category();
		category.setCategoryId(12L);
		category.setCategoryName("Toy");

		categoryRepository.save(category);

		final String baseUrl = "http://localhost:"+port+"/inventory/category";
		URI uri = new URI(baseUrl);

		ResponseEntity<List<CategoryDto>> response = restTemplate.exchange(
				uri,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {
				}
		);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).hasSize(1);
		AssertionsForClassTypes.assertThat(Objects.requireNonNull(response.getBody()).get(0).getCategoryName()).isEqualTo("Toy");


	}


	@Test
	void get_all_product() throws Exception{
		Product product= new Product();

		product.setProductId(5L);
		product.setProductName("Laptop");
		product.setProductType("Machine");
		product.setProductSellingPrice(100L);
		product.setDescription("16 RAM");
		product.setVendor("HP");
		product.setWeight("2");
		product.setWeightUnit("KG");
		product.setBarcode("TEST");
		product.setTag("it");


		productRepository.save(product);

		final String baseUrl = "http://localhost:"+port+"/inventory/product";
		URI uri = new URI(baseUrl);

		ResponseEntity<List<ProductDto>> response = restTemplate.exchange(
				uri,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>() {
				}
		);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).hasSize(1);
		AssertionsForClassTypes.assertThat(Objects.requireNonNull(response.getBody()).get(0).getProductName()).isEqualTo("Laptop");


	}

}
