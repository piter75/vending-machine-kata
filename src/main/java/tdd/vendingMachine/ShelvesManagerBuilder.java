package tdd.vendingMachine;

import tdd.vendingMachine.dto.Item;

import java.util.ArrayList;
import java.util.List;

class ShelvesManagerBuilder {
    private List<Shelf> shelves = new ArrayList<>();

    ShelvesManagerBuilder addShelf(Integer maxItems, Item item, Integer number) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            items.add(item);
        }
        shelves.add(new Shelf(items, maxItems));

        return this;
    }

    ShelvesManager build() {
        return new ShelvesManager(new ArrayList<>(shelves));
    }
}
