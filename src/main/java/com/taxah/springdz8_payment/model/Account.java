package com.taxah.springdz8_payment.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * The Account class represents an entity for managing user accounts.
 */
@Data
@Entity
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private double amount;
}
