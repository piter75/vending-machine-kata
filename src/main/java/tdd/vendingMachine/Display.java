package tdd.vendingMachine;

import tdd.vendingMachine.dto.Message;

import java.math.BigDecimal;

class Display {
    private String contents = "";

    String read() {
        return contents;
    }

    void clear() {
        contents = "";
    }

    void show(String message) {
        contents = message;
    }

    void show(Message message) {
        contents = message.getValue();
    }

    void show(BigDecimal amount) {
        contents = amount.toString();
    }

}
