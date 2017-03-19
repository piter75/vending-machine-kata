package tdd.vendingMachine;

import tdd.vendingMachine.dto.Item;
import tdd.vendingMachine.exceptions.ShelfIsEmptyException;

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

        if (!this.items.isEmpty()) {
            this.price = this.items.get(0).getPrice();
        }
    }

    BigDecimal getPrice() {
        if (items.isEmpty()) {
            throw new ShelfIsEmptyException();
        }

        return price;
    }

    Item getItem() {
        if (items.isEmpty()) {
            throw new ShelfIsEmptyException();
        }

        return items.remove(items.size() - 1);
    }

    void putItems(List<Item> items) {
    }

}
