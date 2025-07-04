package com.example.tradingapp;

import com.example.tradingapp.repositories.DBDigitalAssetRepository;
import com.example.tradingapp.repositories.DBTransactionRepository;
import com.example.tradingapp.repositories.DBUserRepository;
import com.example.tradingapp.repositories.DigitalAssetRepository;
import com.example.tradingapp.repositories.TransactionRepository;
import com.example.tradingapp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
@EnableScheduling
@Configuration
public class TradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingApplication.class, args);
	}

	@Bean
	public UserRepository userRepository() {
		return new DBUserRepository();
	}

	@Bean
	public TransactionRepository transactionRepository() {
		return new DBTransactionRepository();
	}

	@Bean
	public DigitalAssetRepository digitalAssetRepository() {
		return new DBDigitalAssetRepository();
	}

	@Value("${DB_NAME}")
	private String dbName;

	@Value("${DB_USERNAME}")
	private String dbUsername;

	@Value("${DB_PASSWORD}")
	private String dbPassword;

	@Bean
	public Connection connection() {
		Connection connection;
		try	{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName,
				dbUsername,
				dbPassword);
		} catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}
}
