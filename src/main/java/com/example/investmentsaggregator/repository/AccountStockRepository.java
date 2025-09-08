package com.example.investmentsaggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.investmentsaggregator.entity.AccountStock;
import com.example.investmentsaggregator.entity.AccountStockId;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId>{
}
