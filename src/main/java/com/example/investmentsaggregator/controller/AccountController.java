package com.example.investmentsaggregator.controller;

import com.example.investmentsaggregator.controller.dto.AccountResponseDto;
import com.example.investmentsaggregator.controller.dto.AccountStockResponseDto;
import com.example.investmentsaggregator.controller.dto.AssociateAccountStockDto;
import com.example.investmentsaggregator.controller.dto.CreateAccountDto;
import com.example.investmentsaggregator.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/stocks")
    public ResponseEntity<Object> associateStock(@PathVariable("accountId") String accountId,
                                                @RequestBody AssociateAccountStockDto dto){
        accountService.associateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{accountId}/stocks")
    public ResponseEntity<List<AccountStockResponseDto>> listStocks(@PathVariable("accountId") String accountId){
        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }
}
