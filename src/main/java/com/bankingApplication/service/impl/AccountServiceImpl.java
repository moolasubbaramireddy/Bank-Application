package com.bankingApplication.service.impl;

import com.bankingApplication.dto.AccountDto;
import com.bankingApplication.entity.Account;
import com.bankingApplication.mapper.AccountMapper;
import com.bankingApplication.repository.AccountReository;
import com.bankingApplication.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountServiceImpl implements AccountService {

    private AccountReository accountReository;

    public AccountServiceImpl(AccountReository accountReository) {
        this.accountReository = accountReository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account saveAccount = accountReository.save(account);
        return AccountMapper.mapToAccountDto(saveAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountReository.findById(id).
                    orElseThrow(() -> new RuntimeException("Account does not exit in this id"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountReository.findById(id).
                orElseThrow(() -> new RuntimeException("Account does not exit in this id"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account saveAmount = accountReository.save(account);
        return AccountMapper.mapToAccountDto(saveAmount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountReository.findById(id).orElseThrow(() -> new RuntimeException("Account does not exit in this id"));
       if (account.getBalance() < amount){
           throw new RuntimeException("insuffent founds");
       }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account saveAccount = accountReository.save(account);
        return AccountMapper.mapToAccountDto(saveAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountReository.findAll();
       return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }
}
