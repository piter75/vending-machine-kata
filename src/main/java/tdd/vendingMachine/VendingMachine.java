package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.dto.Item;
import tdd.vendingMachine.exceptions.WrongShelfSelectedException;

import java.util.List;

public class VendingMachine implements VendingMachineForUser {
    private String display = "";
    private final CoinsManager coinsManager;
    private final ShelvesManager shelvesManager;

    public VendingMachine(CoinsManager coinsManager, ShelvesManager shelvesManager) {
        this.coinsManager = coinsManager;
        this.shelvesManager = shelvesManager;
    }

    @Override
    public String readDisplay() {
        return display;
    }

    @Override
    public void pickShelf(int shelfNumber) {
        try {
            display = shelvesManager.getItemPrice(shelfNumber).toString();
        } catch (WrongShelfSelectedException e) {
            display = "Wrong shelf number";
        }
    }

    @Override
    public void putCoin(Coin coin) {
    }

    @Override
    public void cancelOrder() {
    }

    @Override
    public List<Coin> getCoins() {
        return null;
    }

    @Override
    public List<Item> getItems() {
        return null;
    }

}
