package com.example.tradingapp.services;

import com.example.tradingapp.exceptions.BadRequestException;
import com.example.tradingapp.model.DigitalAsset;
import com.example.tradingapp.model.Transaction;
import com.example.tradingapp.model.TransactionMethod;
import com.example.tradingapp.model.User;
import com.example.tradingapp.model.dtos.OrderDTO;
import com.example.tradingapp.model.dtos.ResponseUserDTO;
import com.example.tradingapp.repositories.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CryptoCurrencyDataService cryptoCurrencyDataService;
    @Autowired
    private DigitalAssetService digitalAssetService;

    public ResponseUserDTO buy(OrderDTO order) {
        validateOrder(order);
        ResponseUserDTO u = userService.getById(order.getUserId());
        double price = cryptoCurrencyDataService.getCryptoCurrencyDataBySymbol(order.getSymbol()).getBid();

        if (u.getBalance() < price * order.getQuantity()) {
            throw new BadRequestException("Not enough balance");
        }

        DigitalAsset asset = mapper.map(order, DigitalAsset.class);
        digitalAssetService.buy(asset);

        double newBalance = u.getBalance() - price * order.getQuantity();
        User user = userService.updateBalance(order.getUserId(), newBalance);

        saveTransaction(order, TransactionMethod.BUY, price);
        return mapper.map(user, ResponseUserDTO.class);
    }

    public List<Transaction> transactions(int userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public ResponseUserDTO sell(OrderDTO order) {
        validateOrder(order);
        ResponseUserDTO u = userService.getById(order.getUserId());
        double price = cryptoCurrencyDataService.getCryptoCurrencyDataBySymbol(order.getSymbol()).getAsk();

        DigitalAsset asset = mapper.map(order, DigitalAsset.class);
        digitalAssetService.sell(asset);

        double newBalance = u.getBalance() + price * order.getQuantity();
        User user = userService.updateBalance(order.getUserId(), newBalance);

        saveTransaction(order, TransactionMethod.SELL, price);
        return mapper.map(user, ResponseUserDTO.class);
    }

    private void saveTransaction(OrderDTO order, TransactionMethod method, double price) {
        Transaction transaction = mapper.map(order, Transaction.class);
        transaction.setPrice(price);
        transaction.setTransactionMethod(method);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.add(transaction);
    }

    private void validateOrder(OrderDTO order) {
        if (order == null) {
            throw new BadRequestException("Incorrect input");
        }
        if (order.getQuantity() == null || order.getQuantity() < 0.0) {
            throw new BadRequestException("Quantity is mandatory and cannot be less than or equal to 0)");
        }
        if (order.getSymbol() == null || order.getSymbol().isBlank()) {
            throw new BadRequestException("Symbol is mandatory");
        }
        if (order.getUserId() <= 0) {
            throw new BadRequestException("Invalid user id");
        }
    }
}
