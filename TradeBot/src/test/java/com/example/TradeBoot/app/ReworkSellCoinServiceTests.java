package com.example.TradeBoot.app;

import com.example.TradeBoot.api.domain.markets.ESide;
import com.example.TradeBoot.api.http.HttpClientWorker;
import com.example.TradeBoot.api.services.*;
import com.example.TradeBoot.configuration.TestConfig;
import com.example.TradeBoot.configuration.TestServiceInstances;
import com.example.TradeBoot.trade.model.OrderInformation;
import com.example.TradeBoot.trade.model.Persent;
import com.example.TradeBoot.trade.services.FinancialInstrumentPositionsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ReworkSellCoinServiceTests {
    private static HttpClientWorker httpClient;

    private static IPositionsService positionsService;
    private static IOrdersService.Base ordersService;
    private static IMarketService marketService;

    private static IWalletService walletService;

    private static FinancialInstrumentPositionsService financialInstrumentPositionsService;

    @BeforeAll
    static void init() {
        httpClient = TestServiceInstances.getHttpClient();
        positionsService = TestServiceInstances.getPositionsService();
        ordersService = TestServiceInstances.getOrdersService();
        marketService = TestServiceInstances.getMarketService();
        walletService = TestServiceInstances.getWalletService();
        financialInstrumentPositionsService = TestServiceInstances.getFinancialInstrumentPositionsService();

    }
    @Test
    public void print(){
        String market1 = TestConfig.MARKET_NAME;
        String market2 = TestConfig.SHORT_MARKET_NAME;

        System.out.println(marketService.getMarket(market1));
        System.out.println(marketService.getMarket(market2));
    }


    public void canSetLimitOrder() {
//        String marketName = TestConfig.MARKET_NAME;
//        var market = marketService.getMarket(marketName);
//        var orderBook = marketService.getOrderBook(marketName, 5);
//        var incrementSize = market.getSizeIncrement();
//        long tradingDelay = 0;
//
//
//        MarketInformation marketInformation = new MarketInformation(
//                marketName,
//                tradingDelay,
//                new Persent(0.3));
//
//        LocalTradeLoop localTradeLoop = new LocalTradeLoop(
//                ordersService,
//                marketService,
//                new OrderPriceService(),
//                marketInformation
//        );
//        //PrintAccountInfo();
//        //PrintPosition(marketName);
//        //PrintPositions();
//        TestUtils.printBalances();
//        TestUtils.printBalance(marketName);
//        TestUtils.printOpenOrders(marketName);
//
//        var orderInformation = getCoinOrderInformation(marketName);
//        if (orderInformation.isPresent()) {
//            System.out.println("Start trading");
////            tradingService.workWithOrders(orderInformation.get());
//        }
//
//
//        TestUtils.printBalances();
//        TestUtils.printBalance(marketName);
//        TestUtils.printOpenOrders(marketName);
//        System.out.println("END");
    }

    private Optional<List<OrderInformation>> getCoinOrderInformation(String market) {
        var balance = walletService.getBalanceByMarket(market);

        if (balance.isEmpty()) return Optional.empty();

        List<OrderInformation> orderInformations = new ArrayList<>();
        orderInformations.add(
                new OrderInformation(
                        balance.get().getFree(),
                        ESide.SELL,
                        new Persent(5))
        );
        return Optional.of(orderInformations);
    }

//    public void canSellCoint()
//    {
//        String marketName = TestConfig.MARKET_NAME;
//        var market = marketService.getMarket(marketName);
//        var orderBook = marketService.getOrderBook(marketName,5);
//        var incrementSize = market.getSizeIncrement();
//        long tradingDelay = 0;
//
//        CoinSellService coinSellService = createPositionClosingService(incrementSize, tradingDelay);
//
//        printBalances();
//        printBalance(marketName);
//        printOpenOrders(marketName);
//
//        System.out.println("Start closing");
//        coinSellService.handleSell();
//
//        printBalances();
//        printBalance(marketName);
//        printOpenOrders(marketName);
//        System.out.println("END");
//    }



}