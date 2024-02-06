package com.taxah.springdz8_payment.service;


import com.taxah.springdz8_payment.dto.TransferBalance;
import com.taxah.springdz8_payment.model.Account;
import com.taxah.springdz8_payment.model.Product;
import com.taxah.springdz8_payment.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The TransferService class provides methods for handling money transfers between accounts.
 */
@AllArgsConstructor
@Service
public class TransferService {
    private AccountRepository repository;
    private ReservationService reservationService;

    /**
     * Retrieves all accounts.
     *
     * @return A list of accounts.
     */
    public List<Account> getAll() {
        return repository.findAll();
    }

    /**
     * Transfers money from a user to a shop for purchasing a product.
     *
     * @param productId The ID of the product being purchased.
     * @param userId    The ID of the user making the purchase.
     * @param shopId    The ID of the shop receiving the payment.
     * @return A ResponseEntity indicating the result of the transfer.
     */
    @Transactional
    public ResponseEntity<String> transferMoneyToShop(Long productId, Long userId, Long shopId) {
        Optional<Account> userOptional = repository.findById(userId);
        Optional<Account> shopOptional = repository.findById(shopId);
        if (userOptional.isPresent() && shopOptional.isPresent()) {
            Account user = userOptional.get();
            Account shop = shopOptional.get();
            Product product = reservationService.getProduct(productId).getBody();
            double cost;
            if (product != null) {
                cost = product.getCost();
            } else {
                return new ResponseEntity<>("Not found! Check product id", HttpStatus.NOT_FOUND);
            }
            if (user.getAmount() < cost) {
                return new ResponseEntity<>("Not enough money", HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<String> answer = reservationService.reserveProduct(productId);
            if (answer.getStatusCode() != HttpStatus.OK) {
                return answer;
            }
            user.setAmount(user.getAmount() - cost);
            shop.setAmount(shop.getAmount() + cost);
            repository.save(user);
            repository.save(shop);
            return answer;
        }
        return new ResponseEntity<>("Not found! Check users id`s", HttpStatus.NOT_FOUND);
    }

    public TransferBalance getBalance() {
        TransferBalance transferBalance = new TransferBalance();
        transferBalance.setUserBalance(repository.findOneByName("USER").getAmount());
        transferBalance.setShopBalance(repository.findOneByName("SHOP").getAmount());
        return transferBalance;
    }
}
