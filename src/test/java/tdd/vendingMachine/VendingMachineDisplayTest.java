package tdd.vendingMachine;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class VendingMachineDisplayTest {
    private VendingMachineForUser vendingMachine;

    @Before
    public void setup() {
        vendingMachine = new VendingMachine();
    }

    @Test
    public void machine_should_display_correct_price_for_correct_shelf_number() {
        vendingMachine.pickShelf(1);
        String displayedText = vendingMachine.readDisplay();

        Assertions.assertThat(displayedText).isEqualTo("2.50");
    }

    @Test
    public void machine_should_display_error_message_for_incorrect_shelf_number() {
        vendingMachine.pickShelf(500);
        String displayedText = vendingMachine.readDisplay();

        Assertions.assertThat(displayedText).isEqualTo("Wrong shelf number");
    }

    @Test
    public void machine_should_display_error_message_for_negative_shelf_number() {
        vendingMachine.pickShelf(500);
        String displayedText = vendingMachine.readDisplay();

        Assertions.assertThat(displayedText).isEqualTo("Wrong shelf number");
    }

}
