package com.example.tradingapp.repositories;

import com.example.tradingapp.exceptions.BadRequestException;
import com.example.tradingapp.exceptions.UnauthorisedException;
import com.example.tradingapp.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class DBUserRepository implements UserRepository {
    private static final double DEFAULT_BALANCE = 10_000.0;
    @Autowired
    private Connection connection;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private DigitalAssetRepository digitalAssetRepository;

    @Override
    public User findById(int id) {
        PreparedStatement statement = null;
        User user;
        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new BadRequestException("User does not exist");
            }
            user = new User(resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getDouble("balance"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User save(User user) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO users (username, password, balance) " +
                "VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setDouble(3, DEFAULT_BALANCE);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("ERROR in inserting"); //TODO add exception
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            user = new User(generatedKeys.getInt(1),
                user.getUsername(),
                user.getPassword(),
                DEFAULT_BALANCE);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User delete(int id) {
        PreparedStatement statement = null;
        User user = findById(id);
        try {
            statement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    @Transactional
    public User reset(int id) {
        PreparedStatement statement;
        User user;
        try {
            statement = connection.prepareStatement("UPDATE users SET balance=? WHERE id=?");
            statement.setDouble(1, DEFAULT_BALANCE);
            statement.setInt(2, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("ERROR, could not reset user"); //TODO add exception
            }
            transactionRepository.deleteByUserId(id);
            digitalAssetRepository.deleteByUserId(id);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return findById(id);
    }

    @Override
    public User findByUsername(String username) {
        PreparedStatement statement;
        User user;

        try {
            statement = connection.prepareStatement("SELECT * FROM users WHERE username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new UnauthorisedException("Wrong credentials");
            }
            user = new User(resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getDouble("balance"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("SELECT id FROM users WHERE username=?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        }
        return false;
    }

    @Override
    public User updateBalance(int userId, double newBalance) {
        PreparedStatement statement;
        User u;

        try {
            statement = connection.prepareStatement("UPDATE users SET balance=? WHERE id=?");
            statement.setDouble(1, newBalance);
            statement.setInt(2, userId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new RuntimeException("Could not reset");
            }
            statement = connection.prepareStatement("SELECT * FROM users WHERE id=?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            u = new User(resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getDouble("balance"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return u;
    }
}
