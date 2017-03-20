package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.dto.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static tdd.vendingMachine.dto.Message.NO_MONEY_FOR_THE_CHANGE;
import static tdd.vendingMachine.dto.Message.NO_SHELF_SELECTED;

public class VendingMachineOrderTest {
    private VendingMachine vendingMachine;

    @Before
    public void setup() {
        Display display = new Display();

        CoinsManager coinsManager = new CoinsManagerBuilder()
            .addCoins(Coin.TWO, 2)
            .addCoins(Coin.ONE, 2)
            .build();

        ShelvesManager shelvesManager = new ShelvesManagerBuilder()
            .addShelf(10, Item.COKE_025, 10)
            .addShelf(10, Item.PEANUTS, 0)
            .addShelf(10, Item.SNACK, 10)
            .build();

        vendingMachine = new VendingMachine(display, coinsManager, shelvesManager);
    }

    @Test
    public void vending_machine_should_not_start_processing_order_when_shelf_not_selected() {
        vendingMachine.putCoin(Coin.TWO);

        assertThat(vendingMachine.readDisplay()).isEqualTo(NO_SHELF_SELECTED.getValue());
        assertThat(vendingMachine.getCoins()).containsExactly(Coin.TWO);
        assertThat(vendingMachine.getItems()).isEmpty();
    }


    @Test
    public void vending_machine_should_start_processing_order_when_shelf_has_been_selected() {
        vendingMachine.pickShelf(1);
        vendingMachine.putCoin(Coin.ONE);

        assertThat(vendingMachine.readDisplay()).isEqualTo("1.50");
        assertThat(vendingMachine.getCoins()).isEmpty();
        assertThat(vendingMachine.getItems()).isEmpty();
    }

    @Test
    public void vending_machine_should_process_order_when_shelf_has_been_selected_and_exact_money_put() {
        vendingMachine.pickShelf(3);
        vendingMachine.putCoin(Coin.ONE);
        vendingMachine.putCoin(Coin.ONE);

        assertThat(vendingMachine.readDisplay()).isEqualTo("0.00");
        assertThat(vendingMachine.getCoins()).isEmpty();
        assertThat(vendingMachine.getItems()).containsExactly(Item.SNACK);
    }

    @Test
    public void vending_machine_should_process_order_when_shelf_has_been_selected_and_too_much_money_put() {
        vendingMachine.pickShelf(3);
        vendingMachine.putCoin(Coin.ONE);
        vendingMachine.putCoin(Coin.TWO);

        assertThat(vendingMachine.readDisplay()).isEqualTo("-1.00");
        assertThat(vendingMachine.getCoins()).containsExactly(Coin.ONE);
        assertThat(vendingMachine.getItems()).containsExactly(Item.SNACK);
    }

    @Test
    public void vending_machine_should_not_process_order_when_there_are_no_coins_suitable_for_change() {
        vendingMachine.pickShelf(1);
        vendingMachine.putCoin(Coin.TWO);
        vendingMachine.putCoin(Coin.ONE);

        assertThat(vendingMachine.readDisplay()).isEqualTo(NO_MONEY_FOR_THE_CHANGE.getValue());
        assertThat(vendingMachine.getCoins()).containsExactly(Coin.TWO, Coin.ONE);
        assertThat(vendingMachine.getItems()).isEmpty();
    }

    @Test
    public void vending_machine_should_give_back_coins_after_user_cancels_order() {
        vendingMachine.pickShelf(1);
        vendingMachine.putCoin(Coin.TWO);
        vendingMachine.cancelOrder();

        assertThat(vendingMachine.readDisplay()).isEmpty();
        assertThat(vendingMachine.getCoins()).containsExactly(Coin.TWO);
        assertThat(vendingMachine.getItems()).isEmpty();
    }

}
