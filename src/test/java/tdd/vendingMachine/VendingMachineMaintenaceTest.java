package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.dto.Item;
import tdd.vendingMachine.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static tdd.vendingMachine.dto.Coin.*;
import static tdd.vendingMachine.dto.Item.COKE_025;
import static tdd.vendingMachine.dto.Item.PEANUTS;
import static tdd.vendingMachine.dto.Item.SNACK;

public class VendingMachineMaintenaceTest {
    private VendingMachineForUser vendingMachineForUser;
    private VendingMachineForMaintenance vendingMachineForMaintenance;

    @Before
    public void setUp() {
        CoinsManager coinsManager = new CoinsManagerBuilder()
            .addCoins(Coin.TWO, 2)
            .addCoins(Coin.ONE, 2)
            .addCoins(Coin.HALVE, 2)
            .build();

        ShelvesManager shelvesManager = new ShelvesManagerBuilder()
            .addShelf(10, COKE_025, 10)
            .addShelf(10, PEANUTS, 0)
            .addShelf(10, SNACK, 10)
            .build();

        VendingMachine vendingMachine = new VendingMachine(coinsManager, shelvesManager);
        vendingMachineForUser = vendingMachine;
        vendingMachineForMaintenance = vendingMachine;
    }

    @Test
    public void vending_machine_should_not_allow_to_put_coins_when_order_is_in_progress() {
        vendingMachineForUser.pickShelf(1);
        Throwable thrown = catchThrowable(() -> vendingMachineForMaintenance.putCoins(new ArrayList<>()));

        assertThat(thrown).isExactlyInstanceOf(OrderInProgressException.class);
    }

    @Test
    public void vending_machine_should_not_allow_to_get_coins_when_order_is_in_progress() {
        vendingMachineForUser.pickShelf(1);
        Throwable thrown = catchThrowable(() -> vendingMachineForMaintenance.getCoins(FIVE, 1));

        assertThat(thrown).isExactlyInstanceOf(OrderInProgressException.class);
    }

    @Test
    public void vending_machine_should_not_allow_to_put_items_when_order_is_in_progress() {
        vendingMachineForUser.pickShelf(1);
        Throwable thrown = catchThrowable(() -> vendingMachineForMaintenance.putItems(2, new ArrayList<>()));

        assertThat(thrown).isExactlyInstanceOf(OrderInProgressException.class);
    }

    @Test
    public void vending_machine_should_allow_to_put_items_on_empty_shelf() {
        List<Item> items = Collections.singletonList(COKE_025);
        vendingMachineForMaintenance.putItems(2, items);

        vendingMachineForUser.pickShelf(2);
        vendingMachineForUser.putCoin(TWO);
        vendingMachineForUser.putCoin(HALVE);

        assertThat(vendingMachineForUser.getItems()).containsExactly(COKE_025);
    }

    @Test
    public void vending_machine_should_allow_to_put_items_on_shelf_with_same_items() {
        vendingMachineForUser.pickShelf(1);
        vendingMachineForUser.putCoin(TWO);
        vendingMachineForUser.putCoin(HALVE);
        vendingMachineForUser.getItems();

        List<Item> items = Collections.singletonList(COKE_025);
        Throwable thrown = catchThrowable(() -> vendingMachineForMaintenance.putItems(1, items));

        assertThat(thrown).isNull();
    }

    @Test
    public void vending_machine_should_not_allow_to_put_items_on_shelf_with_different_items() {
        List<Item> items = Collections.singletonList(COKE_025);
        Throwable thrown = catchThrowable(() -> vendingMachineForMaintenance.putItems(3, items));

        assertThat(thrown).isExactlyInstanceOf(ShelfIsNotEmptyException.class);
    }

    @Test
    public void vending_machine_should_not_allow_to_put_too_many_items_on_shelf() {
        List<Item> items = Collections.singletonList(COKE_025);
        Throwable thrown = catchThrowable(() -> vendingMachineForMaintenance.putItems(1, items));

        assertThat(thrown).isExactlyInstanceOf(TooManyItemsOnShelfException.class);
    }

    @Test
    public void vending_machine_should_not_change_state_if_no_items_are_put_on_shelf() {
        List<Item> items = Collections.emptyList();
        vendingMachineForMaintenance.putItems(1, items);

        Throwable thrown = catchThrowable(() -> vendingMachineForUser.pickShelf(2));

        assertThat(thrown).isExactlyInstanceOf(ShelfIsEmptyException.class);
    }

    @Test
    public void vending_machine_should_not_allow_to_get_more_coins_than_it_contains() {
        Throwable thrown = catchThrowable(() -> vendingMachineForMaintenance.getCoins(TWO, 3));

        assertThat(thrown).isExactlyInstanceOf(NoMoneyForTheChange.class);
    }

    @Test
    public void vending_machine_should_allow_to_get_less_coins_than_it_contains() {
        vendingMachineForMaintenance.getCoins(TWO, 2);
        vendingMachineForMaintenance.getCoins(ONE, 2);
        vendingMachineForMaintenance.getCoins(HALVE, 2);

        Throwable thrown = catchThrowable(() -> vendingMachineForMaintenance.getCoins(TWO, 1));

        assertThat(thrown).isExactlyInstanceOf(NoMoneyForTheChange.class);
    }

    @Test
    public void vending_machine_should_allow_to_put_coins() {
        vendingMachineForMaintenance.getCoins(TWO, 2);
        vendingMachineForMaintenance.getCoins(ONE, 2);
        vendingMachineForMaintenance.getCoins(HALVE, 2);
        vendingMachineForMaintenance.putCoins(Collections.singletonList(HALVE));

        vendingMachineForUser.pickShelf(1);
        vendingMachineForUser.putCoin(TWO);
        vendingMachineForUser.putCoin(ONE);

        assertThat(vendingMachineForUser.getItems()).containsExactly(COKE_025);
        assertThat(vendingMachineForUser.getCoins()).containsExactly(HALVE);
    }

}
