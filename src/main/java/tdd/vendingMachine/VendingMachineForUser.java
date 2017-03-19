package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.dto.Item;

import java.util.List;

public interface VendingMachineForUser {
    String readDisplay();

    void pickShelf(int shelfNumber);

    void putCoin(Coin coin);

    void cancelOrder();

    List<Coin> getCoins();

    List<Item> getItems();
}
