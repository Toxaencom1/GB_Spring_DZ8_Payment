package com.taxah.springdz8_payment.controllers;

import com.taxah.springdz8_payment.aspects.TrackUserAction;
import com.taxah.springdz8_payment.dto.TransferBalance;
import com.taxah.springdz8_payment.dto.TransferRequest;
import com.taxah.springdz8_payment.facade.TransferFacade;
import com.taxah.springdz8_payment.model.Account;
import com.taxah.springdz8_payment.model.SuccessTransferMetric;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

/**
 * The TransferController class provides endpoints for transferring money and managing accounts.
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/payment")
public class TransferController {
    private TransferFacade transferFacade;
    private SuccessTransferMetric successTransferMetric;

    /**
     * Retrieves all accounts.
     *
     * @return A list of accounts.
     */
    @GetMapping("/accounts")
    public List<Account> getAll() {
        return transferFacade.getAll();
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
            answer = transferFacade.transferMoneyToShop(request.getProductId(),
                    request.getSenderAccountId(),
                    request.getReceiverAccountId());
            if (answer.getStatusCode() == HttpStatus.OK) {
                successTransferMetric.incrementCounter();
                log.info("\n\n===!!!successTransferMetric executed!!!===\n");
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
        return transferFacade.getBalance();
    }

}
