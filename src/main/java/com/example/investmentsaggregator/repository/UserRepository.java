package com.example.investmentsaggregator.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.investmentsaggregator.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
}
