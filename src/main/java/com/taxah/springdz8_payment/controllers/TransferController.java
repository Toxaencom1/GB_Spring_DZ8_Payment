package com.taxah.springdz8_payment.controllers;

import com.taxah.springdz8_payment.aspects.TrackUserAction;
import com.taxah.springdz8_payment.dto.TransferBalance;
import com.taxah.springdz8_payment.dto.TransferRequest;
import com.taxah.springdz8_payment.model.Account;
import com.taxah.springdz8_payment.service.ReservationService;
import com.taxah.springdz8_payment.service.TransferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * The TransferController class provides endpoints for transferring money and managing accounts.
 */
@AllArgsConstructor
@RestController
public class TransferController {
    private ReservationService reservationService;
    private TransferService transferService;

    /**
     * Retrieves all accounts.
     *
     * @return A list of accounts.
     */
    @GetMapping("/accounts")
    public List<Account> getAll() {
        return transferService.getAll();
    }

    /**
     * Transfers money between accounts.
     *
     * @param request The transfer request containing necessary information.
     * @return A ResponseEntity indicating the result of the transfer.
     */
    @TrackUserAction
    @PostMapping("/transfer")
    public ResponseEntity<String> transferMoney(@RequestBody TransferRequest request) {
        ResponseEntity<String> answer;
        try {
            answer = transferService.transferMoneyToShop(request.getProductId(),
                    request.getSenderAccountId(),
                    request.getReceiverAccountId());
            if (answer.getStatusCode() == HttpStatus.OK) {
                return new ResponseEntity<>("Transaction complete, " + answer.getBody(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(answer.getBody(), HttpStatus.BAD_REQUEST);
            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves the balance of the user.
     *
     * @return The balance information.
     */
    @GetMapping("/balance")
    public TransferBalance getUserBalance() {
        return transferService.getBalance();
    }

}
