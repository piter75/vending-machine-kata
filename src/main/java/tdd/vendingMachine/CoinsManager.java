package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.exceptions.NoMoneyForTheChange;

import java.math.BigDecimal;
import java.util.*;

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

    List<Coin> getCoins(Coin coin, Integer number) {
        if (coins.get(coin) < number) {
            throw new NoMoneyForTheChange();
        }

        coins.put(coin, coins.get(coin) - number);

        List<Coin> result = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            result.add(coin);
        }
        return result;
    }

    void putCoins(List<Coin> coins) {
        for (Coin coin: coins) {
            addCoin(coin);
        }
    }

    List<Coin> getChange(BigDecimal value) {
        List<Coin> change = new ArrayList<>();

        BigDecimal remainingValue = processChange(value, change);

        if (remainingValue.compareTo(BigDecimal.ZERO) > 0) {
            putCoins(change);
            throw new NoMoneyForTheChange();
        }

        return change;
    }

    private BigDecimal processChange(BigDecimal value, List<Coin> change) {
        BigDecimal remainingValue = value;
        Iterator<Coin> coinIterator = Arrays.asList(Coin.values()).iterator();

        while (remainingValue.compareTo(BigDecimal.ZERO) > 0 && coinIterator.hasNext()) {
            Coin coin = coinIterator.next();

            int coinsPossible = remainingValue.divideToIntegralValue(coin.getDenomination()).intValue();
            int coinsAvailable = Math.min(coinsPossible, coins.get(coin));

            for (int i = 0; i < coinsAvailable; i++) {
                removeCoin(coin);
                change.add(coin);
                remainingValue = remainingValue.subtract(coin.getDenomination());
            }
        }

        return remainingValue;
    }

    private void addCoin(Coin coin) {
        coins.put(coin, coins.get(coin) + 1);
    }

    private void removeCoin(Coin coin) {
        coins.put(coin, coins.get(coin) - 1);
    }
}
