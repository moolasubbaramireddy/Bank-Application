package com.bankingApplication.repository;

import com.bankingApplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountReository extends JpaRepository<Account,Long> {
}
