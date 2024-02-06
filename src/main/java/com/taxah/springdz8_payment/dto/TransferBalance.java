package com.taxah.springdz8_payment.dto;

import lombok.Data;

/**
 * The TransferBalance class represents balance information for users and the shop.
 */
@Data
public class TransferBalance {
    private double userBalance;
    private double shopBalance;
}
