package tdd.vendingMachine;

import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.dto.Item;
import tdd.vendingMachine.exceptions.NoMoneyForTheChange;
import tdd.vendingMachine.exceptions.OrderInProgressException;
import tdd.vendingMachine.exceptions.WrongShelfSelectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static tdd.vendingMachine.dto.Message.NO_MONEY_FOR_THE_CHANGE;
import static tdd.vendingMachine.dto.Message.NO_SHELF_SELECTED;
import static tdd.vendingMachine.dto.Message.WRONG_SHELF_SELECTED;

class VendingMachine implements VendingMachineForUser, VendingMachineForMaintenance {
    private final Display display;
    private final CoinsManager coinsManager;
    private final ShelvesManager shelvesManager;

    private Integer selectedShelf;
    private BigDecimal outstandingAmount;
    private final List<Coin> orderCoins = new ArrayList<>();
    private final List<Coin> coinDispenser = new ArrayList<>();
    private final List<Item> itemDispenser = new ArrayList<>();

    VendingMachine(Display display, CoinsManager coinsManager, ShelvesManager shelvesManager) {
        this.display = display;
        this.coinsManager = coinsManager;
        this.shelvesManager = shelvesManager;
    }

    @Override
    public String readDisplay() {
        return display.read();
    }

    @Override
    public void pickShelf(int shelfNumber) {
        try {
            selectedShelf = shelfNumber;
            outstandingAmount = shelvesManager.getItemPrice(shelfNumber);
            display.show(outstandingAmount);
        } catch (WrongShelfSelectedException e) {
            display.show(WRONG_SHELF_SELECTED);
        }
    }

    @Override
    public void putCoin(Coin coin) {
        if (selectedShelf == null) {
            display.show(NO_SHELF_SELECTED);
            coinDispenser.add(coin);
            return;
        }

        orderCoins.add(coin);
        outstandingAmount = outstandingAmount.subtract(coin.getDenomination());
        if (outstandingAmount.compareTo(BigDecimal.ZERO) > 0) {
            display.show(outstandingAmount);
        } else {
            processOrder();
        }

    }

    private void processOrder() {
        try {
            List<Coin> change = coinsManager.getChange(outstandingAmount.negate());
            display.show(outstandingAmount);
            coinsManager.putCoins(orderCoins);
            itemDispenser.add(shelvesManager.getItem(selectedShelf));
            cleanupOrderState(change);
        } catch (NoMoneyForTheChange e) {
            display.show(NO_MONEY_FOR_THE_CHANGE);
            cleanupOrderState(orderCoins);
        }

    }

    private void cleanupOrderState(List<Coin> coinsToDispense) {
        selectedShelf = null;
        outstandingAmount = null;
        coinDispenser.addAll(coinsToDispense);
        orderCoins.clear();
    }

    @Override
    public void cancelOrder() {
        display.clear();
        cleanupOrderState(orderCoins);
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

    @Override
    public List<Coin> getCoins(Coin coin, Integer number) {
        checkIfOrderInProgress();
        return coinsManager.getCoins(coin, number);
    }

    @Override
    public void putCoins(List<Coin> coins) {
        checkIfOrderInProgress();
        coinsManager.putCoins(coins);
    }

    @Override
    public void putItems(Integer shelfNumber, List<Item> items) {
        checkIfOrderInProgress();
        shelvesManager.putItemsOnShelf(shelfNumber, items);
    }

    private void checkIfOrderInProgress() {
        if (selectedShelf != null) {
            throw new OrderInProgressException();
        }
    }
}
