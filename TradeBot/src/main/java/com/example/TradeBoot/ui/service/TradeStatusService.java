package com.example.TradeBoot.ui.service;

import com.example.TradeBoot.BigDecimalUtils;
import com.example.TradeBoot.api.services.OrdersService;
import com.example.TradeBoot.api.services.WalletService;
import com.example.TradeBoot.ui.ITradeSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TradeStatusService {

    @Autowired
    public TradeStatusService(ITradeSettingsService tradeSettingsService, WalletService walletService, OrdersService ordersService) {
        this.tradeSettingsService = tradeSettingsService;
        this.walletService = walletService;
        this.ordersService = ordersService;
    }


    private ITradeSettingsService tradeSettingsService;

    private WalletService walletService;

    private OrdersService ordersService;


    private List<String> ignoredBalanceNames = List.of("USD", "EUR");

    public List<MarketOpenOrderSize> getOpenOrdersByConfiguration() {
        var openOrdersMap = StreamSupport.stream(tradeSettingsService.findAll().spliterator(), false)
                .map(tradeSettings -> tradeSettings.getMarketName())
                .map(ordersService::getOpenOrders)
                .filter(openOrders -> openOrders.size() > 0)
                .map(openOrders -> {
                    var firstOrder = openOrders.stream().findFirst().orElseThrow();
                    return new MarketOpenOrderSize(firstOrder.getMarket(), openOrders.size());
                })
                .collect(Collectors.toList());

        return openOrdersMap;
    }

    public record MarketOpenOrderSize(String marketName, int size) {
    }

    public List<OpenPositionInfo> getOpenPositions() {

        var openPositions = walletService.getBalances()
                .stream()
                .filter(balance -> ignoredBalanceNames.contains(balance.getCoin()) == false)
                .filter(balance -> {
                    return BigDecimalUtils.check(balance.getTotal(), BigDecimalUtils.EOperator.EQUALS, BigDecimal.ZERO) == false;
                })
                .map(balance -> new OpenPositionInfo(balance.getCoin(), balance.getTotal()))
                .collect(Collectors.toList());

        return openPositions;
    }

    public record OpenPositionInfo(String marketName, BigDecimal total) {
    }

    public long getTradeSettingsCount() {
        return StreamSupport.stream(tradeSettingsService.findAll().spliterator(), false).count();
    }


}