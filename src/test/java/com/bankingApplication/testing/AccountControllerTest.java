package com.bankingApplication.testing;

import com.bankingApplication.controller.AccountController;
import com.bankingApplication.dto.AccountDto;
import com.bankingApplication.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddAccount() {
        // Prepare data
        AccountDto accountDto = new AccountDto();
        when(accountService.createAccount(accountDto)).thenReturn(accountDto);

        // Perform the test
        ResponseEntity<AccountDto> response = accountController.addAccount(accountDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
        verify(accountService, times(1)).createAccount(accountDto);
    }

    @Test
    void testGetAccountById() {
        // Prepare data
        Long accountId = 1L;
        AccountDto accountDto = new AccountDto();
        when(accountService.getAccountById(accountId)).thenReturn(accountDto);

        // Perform the test
        ResponseEntity<AccountDto> response = accountController.getAccountById(accountId);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
        verify(accountService, times(1)).getAccountById(accountId);
    }

    @Test
    void testDepositAmount() {
        // Prepare data
        Long accountId = 1L;
        Double amount = 100.0;
        AccountDto accountDto = new AccountDto();
        when(accountService.deposit(accountId, amount)).thenReturn(accountDto);
        Map<String, Double> request = Collections.singletonMap("amount", amount);

        // Perform the test
        ResponseEntity<AccountDto> response = accountController.DepositAmount(accountId, request);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
        verify(accountService, times(1)).deposit(accountId, amount);
    }

    @Test
    void testWithdrawAmount() {
        // Prepare data
        Long accountId = 1L;
        Double amount = 100.0;
        AccountDto accountDto = new AccountDto();
        when(accountService.withdraw(accountId, amount)).thenReturn(accountDto);
        Map<String, Double> request = Collections.singletonMap("amount", amount);

        // Perform the test
        ResponseEntity<AccountDto> response = accountController.withdrawAmount(accountId, request);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDto, response.getBody());
        verify(accountService, times(1)).withdraw(accountId, amount);
    }

    @Test
    void testGetAllAccounts() {
        // Prepare data
        List<AccountDto> accountDtoList = Collections.singletonList(new AccountDto());
        when(accountService.getAllAccounts()).thenReturn(accountDtoList);

        // Perform the test
        ResponseEntity<List<AccountDto>> response = accountController.getAllAccounts();

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountDtoList, response.getBody());
        verify(accountService, times(1)).getAllAccounts();
    }
}
