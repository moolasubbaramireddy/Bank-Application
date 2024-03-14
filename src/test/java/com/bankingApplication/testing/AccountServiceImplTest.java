package com.bankingApplication.testing;


import com.bankingApplication.dto.AccountDto;
import com.bankingApplication.entity.Account;
import com.bankingApplication.mapper.AccountMapper;

import com.bankingApplication.repository.AccountReository;
import com.bankingApplication.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {
    @Mock
    private AccountReository accountReository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createAccount(){
        AccountDto accountDto=new AccountDto(1L,"Johan ram",10000.0);
        Account account= AccountMapper.mapToAccount(accountDto);
        when(accountReository.save(any(Account.class))).thenReturn(account);

      AccountDto savedAccountDto=  accountService.createAccount(accountDto);

      assertNotNull(savedAccountDto);
      assertEquals(accountDto.getId(),savedAccountDto.getId());
      assertEquals(accountDto.getAccountHolderName(),savedAccountDto.getAccountHolderName());
      assertEquals(accountDto.getBalance(),savedAccountDto.getBalance());
      verify(accountReository,times(1)).save(any(Account.class));
    }

    @Test
    public void getAccountById(){
        Long accountId=1L;

        Account account = new Account(accountId,"subbu",1000.0);
        when(accountReository.findById(accountId)).thenReturn(Optional.of(account));

        AccountDto retrieveedAccountDto = accountService.getAccountById(accountId);
        assertNotNull(retrieveedAccountDto);
        assertEquals(accountId,retrieveedAccountDto.getId());
        assertEquals(account.getAccountHolderName(),retrieveedAccountDto.getAccountHolderName());
        assertEquals(account.getBalance(),retrieveedAccountDto.getBalance());
        verify(accountReository,times(1)).findById(accountId);
    }

    @Test
    public void deposit(){
        //prepare test data
        Long accountId=1L;
        double initialBalance=1000.0;
        double depositAmount=500.0;
        Account account=new Account(accountId,"subbu",initialBalance);

        //mock behavior of accountRepository
        when(accountReository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountReository.save(any(Account.class))).thenReturn(account);

        //perform the deposit
        AccountDto updateaccountDto=accountService.deposit(accountId,depositAmount);


        //Verify the result
        assertEquals(accountId,updateaccountDto.getId());
        assertEquals(account.getAccountHolderName(),updateaccountDto.getAccountHolderName());
        assertEquals(initialBalance+depositAmount,updateaccountDto.getBalance());

    }

    @Test
    public void withdraw(){
        //Prepare test data
        Long accountId=1L;
        double initialBalance=1000.0;
        double withdrawalAmount=500.0;
        Account account=new Account(accountId,"subbu",initialBalance);

        //Mock behavior of accountRepository
        when(accountReository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountReository.save(any(Account.class))).thenReturn(account);


        //perform the withdeawal
        AccountDto updateAccountDto=accountService.withdraw(accountId,withdrawalAmount);

        //verify the result
        assertEquals(accountId,updateAccountDto.getId());
        assertEquals(account.getAccountHolderName(),updateAccountDto.getAccountHolderName());
        assertEquals(initialBalance-withdrawalAmount,updateAccountDto.getBalance());
    }

    @Test
    public void getAllAccounts(){
        Account account=new Account(1L,"subbu",1000.0);
        when(accountReository.findAll()).thenReturn(Collections.singletonList(account));

        List<AccountDto> accountDtoList = accountService.getAllAccounts();

        assertNotNull(accountDtoList);
        assertEquals(1,accountDtoList.size());
        assertEquals(account.getId(),accountDtoList.get(0).getId());
        assertEquals(account.getAccountHolderName(),accountDtoList.get(0).getAccountHolderName());
        assertEquals(account.getBalance(),accountDtoList.get(0).getBalance());
        verify(accountReository,times(1)).findAll();
    }
}
