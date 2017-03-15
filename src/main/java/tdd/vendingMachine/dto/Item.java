package tdd.vendingMachine.dto;

import java.math.BigDecimal;

public enum Item {
    COKE_025("Coke 0,25l", new BigDecimal("2.50")),
    SNACK("Snack", new BigDecimal("2.00")),
    PEANUTS("Peanuts", new BigDecimal("1.50"));

    private String name;
    private BigDecimal price;

    Item(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
