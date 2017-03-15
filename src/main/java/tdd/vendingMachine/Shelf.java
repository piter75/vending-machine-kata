package tdd.vendingMachine;

import tdd.vendingMachine.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class Shelf {
    private BigDecimal price;
    private final List<Item> items;
    private final Integer maxItems;

    Shelf(List<Item> items, Integer maxItems) {
        this.items = new ArrayList<>(items);
        this.maxItems = maxItems;
    }

    BigDecimal getPrice() {
        return null;
    }
}
