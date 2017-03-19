package tdd.vendingMachine;

import tdd.vendingMachine.exceptions.WrongShelfSelectedException;

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

}
