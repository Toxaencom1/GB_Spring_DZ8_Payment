package com.taxah.springdz8_payment.service;


import com.taxah.springdz8_payment.model.Product;

import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 * The ApiGatewayService class provides methods to interact with the reservation functionalities.
 */
@AllArgsConstructor
@Service
public class ApiGatewayService {
    private static final String GATEWAY = "http://localhost:8765";
    private RestTemplate template;

    /**
     * Reserves a product with the specified ID.
     *
     * @param id The ID of the product to be reserved.
     * @return A ResponseEntity containing information about the reservation.
     */
    public ResponseEntity<String> reserveProduct(Long id) {
        String url = GATEWAY + "/store/reserve/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        return template.exchange(
                url, HttpMethod.PUT, requestEntity, String.class);
    }

    /**
     * Retrieves a product with the specified ID.
     *
     * @param id The ID of the product to retrieve.
     * @return A ResponseEntity containing the product information.
     */
    public ResponseEntity<Product> getProduct(Long id) {
        String url = GATEWAY + "/store/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        return template.exchange(
                url, HttpMethod.GET, requestEntity, Product.class);
    }
}
