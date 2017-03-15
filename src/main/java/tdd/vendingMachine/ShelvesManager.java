package tdd.vendingMachine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ShelvesManager {
    private final Map<Integer, Shelf> shelvesMap;

    ShelvesManager(List<Shelf> shelves) {
        shelvesMap = new HashMap<>();
    }

    BigDecimal getItemPrice(Integer shelfNumber) {
        return null;
    }

}
