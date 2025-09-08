package com.example.investmentsaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InvestmentsaggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestmentsaggregatorApplication.class, args);
	}

}
