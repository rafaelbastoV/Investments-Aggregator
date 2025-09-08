package com.example.investmentsaggregator.service;

import com.example.investmentsaggregator.client.BrapiClient;
import com.example.investmentsaggregator.controller.dto.AccountResponseDto;
import com.example.investmentsaggregator.controller.dto.AccountStockResponseDto;
import com.example.investmentsaggregator.controller.dto.AssociateAccountStockDto;
import com.example.investmentsaggregator.entity.AccountStock;
import com.example.investmentsaggregator.entity.AccountStockId;
import com.example.investmentsaggregator.entity.User;
import com.example.investmentsaggregator.repository.AccountRepository;
import com.example.investmentsaggregator.repository.AccountStockRepository;
import com.example.investmentsaggregator.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;

    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;
    private BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository,
                          AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }


    public void associateStock(String accountId, AssociateAccountStockDto dto) {

        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //dto -> entity
        var id = new AccountStockId(account.getAccountId(), dto.stockId());
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );
        accountStockRepository.save(entity);

    }

    public List<AccountStockResponseDto> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStocks()
                .stream()
                .map(as -> new AccountStockResponseDto(
                        as.getStock().getStockId(), as.getQuantity(),
                        getTotal(as.getStock().getStockId(), as.getQuantity())))
                .toList();
    }

    private double getTotal(String stockId, Integer quantity) {
        var response = brapiClient.getQuote(TOKEN, stockId);
        var price = response.results().getFirst().regularMarketPrice();
        return price * quantity;
    }
}
