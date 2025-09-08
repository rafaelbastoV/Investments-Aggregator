package com.example.investmentsaggregator.service;

import com.example.investmentsaggregator.controller.dto.CreateStockDto;
import com.example.investmentsaggregator.entity.Stock;
import com.example.investmentsaggregator.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public void createStock(CreateStockDto createStockDto) {
        //DTO -> ENTITY
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}
