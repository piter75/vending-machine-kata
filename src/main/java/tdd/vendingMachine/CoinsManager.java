package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.exceptions.NoMoneyForTheChange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CoinsManager {
    private final Map<Coin, Integer> coins;

    CoinsManager(List<Coin> coins) {
        this.coins = new HashMap<>();
        for (Coin coin : Coin.values()) {
            this.coins.put(coin, 0);
        }

        coins.forEach(coin ->
            this.coins.put(coin, this.coins.getOrDefault(coin, 0) + 1)
        );
    }

    void putCoins(List<Coin> coins) {
        for (Coin coin: coins) {
            this.coins.put(coin, this.coins.get(coin) + 1);
        }
    }

    List<Coin> getChange(BigDecimal value) {
        BigDecimal remainingValue = value;
        List<Coin> change = new ArrayList<>();

        for (Coin coin: Coin.values()) {
            if (coins.getOrDefault(coin, 0) == 0)
                continue;

            int coinsPossible = remainingValue.divideToIntegralValue(coin.getDenomination()).intValue();
            int coinsAvailable = Math.min(coinsPossible, coins.get(coin));

            for (int i = 0; i < coinsAvailable; i++) {
                if (coins.get(coin) > 0) {
                    coins.put(coin, coins.get(coin) - 1);
                    change.add(coin);
                    remainingValue = remainingValue.subtract(coin.getDenomination());
                }
            }

            if (remainingValue.compareTo(BigDecimal.ZERO) == 0)
                break;
        }

        if (remainingValue.compareTo(BigDecimal.ZERO) != 0) {
            for (Coin coin: change) {
                coins.put(coin, coins.get(coin) + 1);
            }
            throw new NoMoneyForTheChange();
        }

        return change;
    }
}
