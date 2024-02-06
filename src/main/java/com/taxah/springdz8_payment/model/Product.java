package com.taxah.springdz8_payment.model;

import lombok.Data;


/**
 * The Product class represents a product entity.
 */
@Data
public class Product {
    private Long id;
    private String name;
    private Double cost;
    private boolean status = false;
}
