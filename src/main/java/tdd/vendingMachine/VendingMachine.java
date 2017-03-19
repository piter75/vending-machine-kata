package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.dto.Item;
import tdd.vendingMachine.exceptions.NoMoneyForTheChange;
import tdd.vendingMachine.exceptions.WrongShelfSelectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static tdd.vendingMachine.dto.Message.NO_MONEY_FOR_THE_CHANGE;
import static tdd.vendingMachine.dto.Message.NO_SHELF_SELECTED;

public class VendingMachine implements VendingMachineForUser {
    private String display = "";
    private final CoinsManager coinsManager;
    private final ShelvesManager shelvesManager;

    private Integer selectedShelf;
    private BigDecimal outstandingAmount;
    private final List<Coin> orderCoins = new ArrayList<>();
    private final List<Coin> coinDispenser = new ArrayList<>();
    private final List<Item> itemDispenser = new ArrayList<>();

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
            selectedShelf = shelfNumber;
            outstandingAmount = shelvesManager.getItemPrice(shelfNumber);
            display = outstandingAmount.toString();
        } catch (WrongShelfSelectedException e) {
            display = "Wrong shelf number";
        }
    }

    @Override
    public void putCoin(Coin coin) {
        if (selectedShelf == null) {
            display = NO_SHELF_SELECTED.getValue();
            coinDispenser.add(coin);
            return;
        }

        orderCoins.add(coin);
        outstandingAmount = outstandingAmount.subtract(coin.getDenomination());
        if (outstandingAmount.compareTo(BigDecimal.ZERO) > 0) {
            display = outstandingAmount.toString();
        } else {
            processOrder();
        }

    }

    private void processOrder() {
        try {
            List<Coin> change = coinsManager.getChange(outstandingAmount.negate());
            display = outstandingAmount.toString();
            coinsManager.putCoins(orderCoins);
            itemDispenser.add(shelvesManager.getItem(selectedShelf));
            selectedShelf = null;
            outstandingAmount = null;
            orderCoins.clear();
            coinDispenser.addAll(change);
        } catch (NoMoneyForTheChange e) {
            display = "";
            selectedShelf = null;
            outstandingAmount = null;
            coinDispenser.addAll(orderCoins);
            orderCoins.clear();
            display = NO_MONEY_FOR_THE_CHANGE.getValue();
        }

    }

    @Override
    public void cancelOrder() {
        display = "";
        selectedShelf = null;
        outstandingAmount = null;
        coinDispenser.addAll(orderCoins);
        orderCoins.clear();
    }

    @Override
    public List<Coin> getCoins() {
        List<Coin> coins = new ArrayList<>(coinDispenser);
        coinDispenser.clear();

        return coins;
    }

    @Override
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>(itemDispenser);
        itemDispenser.clear();

        return items;
    }

}
