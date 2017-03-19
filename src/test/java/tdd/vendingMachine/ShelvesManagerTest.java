package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.dto.Item;
import tdd.vendingMachine.exceptions.ShelfIsEmptyException;
import tdd.vendingMachine.exceptions.WrongShelfSelectedException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static tdd.vendingMachine.dto.Item.COKE_025;
import static tdd.vendingMachine.dto.Item.PEANUTS;
import static tdd.vendingMachine.dto.Item.SNACK;

public class ShelvesManagerTest {
    private ShelvesManager shelvesManager;

    @Before
    public void setup() {
        ShelvesManagerBuilder shelvesManagerBuilder = new ShelvesManagerBuilder();
        shelvesManagerBuilder
            .addShelf(10, COKE_025, 10)
            .addShelf(10, PEANUTS, 0)
            .addShelf(10, SNACK, 10);
        shelvesManager = shelvesManagerBuilder.build();
    }

    @Test
    public void shelves_manager_should_return_correct_price_for_first_shelf() {
        BigDecimal itemPrice = shelvesManager.getItemPrice(1);

        assertThat(itemPrice).isEqualTo(COKE_025.getPrice());
    }

    @Test
    public void shelves_manager_should_return_correct_price_for_third_shelf() {
        BigDecimal itemPrice = shelvesManager.getItemPrice(3);

        assertThat(itemPrice).isEqualTo(SNACK.getPrice());
    }

    @Test
    public void shelves_manager_should_throw_correct_exception_for_nonexistent_shelf() {
        Throwable thrown = catchThrowable(() -> shelvesManager.getItemPrice(4));

        assertThat(thrown).isExactlyInstanceOf(WrongShelfSelectedException.class);
    }

    @Test
    public void shelves_manager_should_throw_correct_exception_for_negative_shelf_number() {
        Throwable thrown = catchThrowable(() -> shelvesManager.getItemPrice(-1));

        assertThat(thrown).isExactlyInstanceOf(WrongShelfSelectedException.class);
    }

    @Test
    public void shelves_manager_should_pass_correct_exception_for_empty_shelf_selection() {
        Throwable thrown = catchThrowable(() -> shelvesManager.getItemPrice(2));

        assertThat(thrown).isExactlyInstanceOf(ShelfIsEmptyException.class);
    }
}
