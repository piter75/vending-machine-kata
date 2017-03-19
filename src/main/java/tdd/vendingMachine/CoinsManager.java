package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CoinsManager {
    private final Map<Coin, Integer> coins;

    CoinsManager(List<Coin> coins) {
        this.coins = new HashMap<>();
    }

    void putCoins(List<Coin> coins) {
    }

    List<Coin> getChange(BigDecimal value) {
        return null;
    }
}
