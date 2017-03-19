package tdd.vendingMachine;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static tdd.vendingMachine.dto.Item.COKE_025;
import static tdd.vendingMachine.dto.Message.WRONG_SHELF_SELECTED;

public class VendingMachineDisplayTest {
    private VendingMachineForUser vendingMachine;

    @Before
    public void setup() {
        ShelvesManagerBuilder shelvesManagerBuilder = new ShelvesManagerBuilder();
        ShelvesManager shelvesManager = shelvesManagerBuilder
            .addShelf(10, COKE_025, 10)
            .build();
        CoinsManagerBuilder coinsManagerBuilder = new CoinsManagerBuilder();
        CoinsManager coinsManager = coinsManagerBuilder.build();

        vendingMachine = new VendingMachine(coinsManager, shelvesManager);
    }

    @Test
    public void machine_should_not_display_anything_when_no_shelf_selected() {
        String displayedText = vendingMachine.readDisplay();

        Assertions.assertThat(displayedText).isEqualTo("");
    }

    @Test
    public void machine_should_display_correct_price_for_correct_shelf_number() {
        vendingMachine.pickShelf(1);
        String displayedText = vendingMachine.readDisplay();

        Assertions.assertThat(displayedText).isEqualTo(COKE_025.getPriceAsString());
    }

    @Test
    public void machine_should_display_error_message_for_incorrect_shelf_number() {
        vendingMachine.pickShelf(500);
        String displayedText = vendingMachine.readDisplay();

        Assertions.assertThat(displayedText).isEqualTo(WRONG_SHELF_SELECTED.getValue());
    }

    @Test
    public void machine_should_display_error_message_for_negative_shelf_number() {
        vendingMachine.pickShelf(-1);
        String displayedText = vendingMachine.readDisplay();

        Assertions.assertThat(displayedText).isEqualTo(WRONG_SHELF_SELECTED.getValue());
    }

}
