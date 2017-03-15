package tdd.vendingMachine.dto;

public enum Message {
    WRONG_SHELF_SELECTED("Wrong shelf number")
    ;

    String value;

    Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
