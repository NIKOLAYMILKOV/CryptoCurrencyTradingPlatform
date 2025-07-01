package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.repositories.CustomRepository;
import com.example.demo.repositories.DBUserRepository;
import org.modelmapper.ModelMapper;
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
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	public UserRepository userRepository() {
//		return new DBUserRepository();
//	}

	@Bean
	public CustomRepository<User> userRepository() {
		return new DBUserRepository();
	}

	@Bean
	public Connection connection() {

		Connection connection;
		try	{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trading",
				"root",
				"2wsx#EDC");
		} catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
