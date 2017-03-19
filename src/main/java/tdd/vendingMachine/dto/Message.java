package tdd.vendingMachine.dto;

public enum Message {
    WRONG_SHELF_SELECTED("Wrong shelf number"),
    NO_SHELF_SELECTED("No shelf selected!"),
    NO_MONEY_FOR_THE_CHANGE("No money for the change!")
    ;

    String value;

    Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
