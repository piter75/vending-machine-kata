package tdd.vendingMachine;

import tdd.vendingMachine.dto.Item;
import tdd.vendingMachine.exceptions.WrongShelfSelectedException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ShelvesManager {
    private final Map<Integer, Shelf> shelvesMap;

    ShelvesManager(List<Shelf> shelves) {
        shelvesMap = new HashMap<>();

        for (int i = 0; i < shelves.size(); i++) {
            shelvesMap.put(i + 1, shelves.get(i));
        }
    }

    BigDecimal getItemPrice(Integer shelfNumber) {
        checkShelfNumber(shelfNumber);

        return shelvesMap.get(shelfNumber).getPrice();
    }

    Item getItem(Integer shelfNumber) {
        checkShelfNumber(shelfNumber);

        return shelvesMap.get(shelfNumber).getItem();
    }

    private void checkShelfNumber(Integer shelfNumber) {
        if (!shelvesMap.containsKey(shelfNumber))
            throw new WrongShelfSelectedException();
    }
}
