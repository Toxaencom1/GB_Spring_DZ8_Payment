package com.taxah.springdz8_payment.repository;

import com.taxah.springdz8_payment.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The AccountRepository interface provides CRUD operations for managing Account entities.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Retrieves an account by its name.
     *
     * @param name The name of the account to retrieve.
     * @return The account corresponding to the name.
     */
    Account findOneByName(String name);
}
