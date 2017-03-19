package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.dto.Item;

import java.util.List;

interface VendingMachineForMaintenance {
    List<Coin> getCoins(Coin coin, Integer number);

    void putCoins(List<Coin> coins);

    void putItems(Integer shelfNumber, List<Item> items);
}
