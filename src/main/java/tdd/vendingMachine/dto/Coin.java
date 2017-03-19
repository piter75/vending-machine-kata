package tdd.vendingMachine.dto;

import java.math.BigDecimal;

public enum Coin {
    FIVE("5"),
    TWO("2"),
    ONE("1"),
    HALVE("0.5"),
    FIFTH("0.2"),
    TENTH("0.1"),
    ;

    private BigDecimal denomination;

    Coin(String value) {
        this.denomination = new BigDecimal(value);
    }

    public BigDecimal getDenomination() {
        return denomination;
    }
}
