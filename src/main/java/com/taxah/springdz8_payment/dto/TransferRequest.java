package com.taxah.springdz8_payment.dto;

import lombok.Data;

/**
 * The TransferRequest class represents a request for transferring money between accounts.
 */
@Data
public class TransferRequest {
    private long productId;
    private long senderAccountId;
    private long receiverAccountId;
}
