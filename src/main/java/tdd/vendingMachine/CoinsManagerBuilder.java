package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;

import java.util.ArrayList;
import java.util.List;

class CoinsManagerBuilder {
    private List<Coin> coins = new ArrayList<>();

    CoinsManagerBuilder addCoins(Coin coin, Integer number) {
        for (int i = 0; i < number; i++) {
            coins.add(coin);
        }

        return this;
    }

    CoinsManager build() {
        return new CoinsManager(new ArrayList<>(coins));
    }
}
