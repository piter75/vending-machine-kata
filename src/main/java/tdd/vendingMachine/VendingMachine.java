package tdd.vendingMachine;

import tdd.vendingMachine.dto.Item;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine implements VendingMachineForUser {
    private String display = "";
    private final Map<Integer, Item> shelves = new HashMap<>();


    public VendingMachine() {
        shelves.put(1, Item.COKE_025);
    }

    @Override
    public String readDisplay() {
        return display;
    }

    @Override
    public void pickShelf(int shelfNumber) {
        if (!shelves.containsKey(shelfNumber)) {
            display = "Wrong shelf number";
            return;
        }

        display = shelves.get(shelfNumber).getPriceAsString();
    }

}
