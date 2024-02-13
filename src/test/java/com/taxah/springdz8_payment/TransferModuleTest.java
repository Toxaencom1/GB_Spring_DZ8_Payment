package com.taxah.springdz8_payment;

import com.taxah.springdz8_payment.facade.TransferFacade;
import com.taxah.springdz8_payment.model.Account;
import com.taxah.springdz8_payment.model.Product;
import com.taxah.springdz8_payment.repository.AccountRepository;
import com.taxah.springdz8_payment.service.ApiGatewayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

/**
 * Integration tests for the TransferFacade class.
 */
@ExtendWith(MockitoExtension.class)
class TransferModuleTest {

    @Mock
    private AccountRepository repository;
    @Mock
    private ApiGatewayService service;
    @InjectMocks
    private TransferFacade facade;

    private Account user;
    private Account shop;
    private Product product;
    Long productId;
    Long userId;
    Long shopId;

    /**
     * Setup method to initialize mock entities before each test.
     */
    @BeforeEach
    void setup() {
        user = new Account();        shop = new Account();
        user.setId(1L);              shop.setId(2L);
        user.setName("USER");        shop.setName("SHOP");
        user.setAmount(2500.0);      shop.setAmount(10000.0);

        product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        product.setCost(2000.0);

        productId = 1L;
        userId = 1L;
        shopId = 2L;
    }

    /**
     * Test method for findById failure scenario.
     */
    @Test
    void findByIdFailTest(){
        Optional<Account> optional = Optional.empty();
        ResponseEntity<String> expected = new ResponseEntity<>("Not found! Check users id`s", HttpStatus.NOT_FOUND);
        given(repository.findById(1L)).willReturn(optional);
        given(repository.findById(2L)).willReturn(optional);

        ResponseEntity<String> resultEntity = facade.transferMoneyToShop(productId,userId,shopId);

        verify(repository).findById(2L);
        assertEquals(expected,resultEntity);
    }

    /**
     * Test method for getProduct failure scenario.
     */
    @Test
    void getProductFailTest(){
        ResponseEntity<Product> answer = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        ResponseEntity<String> expected = new ResponseEntity<>("Not found! Check product id", HttpStatus.NOT_FOUND);
        Optional<Account> userOptional = Optional.of(user);
        Optional<Account> shopOptional = Optional.of(shop);
        given(service.getProduct(1L)).willReturn(answer);
        given(repository.findById(userId)).willReturn(userOptional);
        given(repository.findById(shopId)).willReturn(shopOptional);

        ResponseEntity<String> resultEntity = facade.transferMoneyToShop(productId,userId,shopId);

        verify(service).getProduct(1L);
        assertEquals(expected,resultEntity);
    }

    /**
     * Test method for user 'not enough money' failure scenario.
     */
    @Test
    void userAmountFailTest(){
        ResponseEntity<Product> answerEntity = new ResponseEntity<>(product, HttpStatus.OK);
        ResponseEntity<String> expected = new ResponseEntity<>("Not enough money", HttpStatus.BAD_REQUEST);
        Optional<Account> userOptional = Optional.of(user);
        Optional<Account> shopOptional = Optional.of(shop);
        user.setAmount(0.0);
        given(service.getProduct(productId)).willReturn(answerEntity);
        given(repository.findById(userId)).willReturn(userOptional);
        given(repository.findById(shopId)).willReturn(shopOptional);

        ResponseEntity<String> resultEntity = facade.transferMoneyToShop(productId,userId,shopId);

        assertEquals(expected,resultEntity);
    }

    /**
     * Test method for reserveProduct failure scenario.
     */
    @Test
    void reserveProductFailTest(){
        ResponseEntity<Product> answerEntity = new ResponseEntity<>(product, HttpStatus.OK);
        ResponseEntity<String> expected = new ResponseEntity<>("Product already reserved", HttpStatus.BAD_REQUEST);
        Optional<Account> userOptional = Optional.of(user);
        Optional<Account> shopOptional = Optional.of(shop);
        product.setStatus(true);
        given(repository.findById(userId)).willReturn(userOptional);
        given(repository.findById(shopId)).willReturn(shopOptional);
        given(service.getProduct(productId)).willReturn(answerEntity);
        given(service.reserveProduct(productId)).willReturn(expected);

        ResponseEntity<String> resultEntity = facade.transferMoneyToShop(productId,userId,shopId);

        assertEquals(expected,resultEntity);
    }

    /**
     * Test method for successful money transfer.
     */
    @Test
    void fullTransferPositiveTest() {
        ResponseEntity<Product> answerEntity = new ResponseEntity<>(product, HttpStatus.OK);
        ResponseEntity<String> expected = new ResponseEntity<>("Product reserved", HttpStatus.OK);
        Optional<Account> userOptional = Optional.of(user);
        Optional<Account> shopOptional = Optional.of(shop);
        Double userAmount = user.getAmount();
        Double shopAmount = shop.getAmount();
        given(repository.findById(userId)).willReturn(userOptional);
        given(repository.findById(shopId)).willReturn(shopOptional);
        given(service.getProduct(productId)).willReturn(answerEntity);
        given(service.reserveProduct(productId)).willReturn(expected);

        ResponseEntity<String> resultEntity = facade.transferMoneyToShop(productId, userId, shopId);

        verify(repository).findById(userId);
        verify(repository).findById(shopId);
        verify(service).getProduct(productId);
        verify(service).reserveProduct(productId);
        assertEquals(userAmount - product.getCost(), user.getAmount());
        assertEquals(shopAmount + product.getCost(), shop.getAmount());
        verify(repository).save(user);
        verify(repository).save(shop);
        assertEquals(expected,resultEntity);
    }
}
