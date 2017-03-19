package tdd.vendingMachine;

import org.junit.Before;
import org.junit.Test;
import tdd.vendingMachine.dto.Coin;
import tdd.vendingMachine.exceptions.NoMoneyForTheChange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static tdd.vendingMachine.dto.Coin.*;

public class CoinsManagerTest {
    private CoinsManager coinsManager;

    @Before
    public void setUp() {
        List<Coin> coins = new ArrayList<>();
        coinsManager = new CoinsManager(coins);
    }

    @Test
    public void empty_coins_manager_should_throw_correct_exception_when_asked_for_nonzero_change() {
        Throwable thrown = catchThrowable(() -> coinsManager.getChange(new BigDecimal("2.0")));

        assertThat(thrown).isExactlyInstanceOf(NoMoneyForTheChange.class);
    }

    @Test
    public void empty_coins_manager_should_return_empty_list_when_asked_for_zero_change() {
        List<Coin> change = coinsManager.getChange(new BigDecimal("0.0"));

        assertThat(change).isEmpty();
    }

    @Test
    public void coins_manager_should_return_correct_change_amount() {
        coinsManager.putCoins(Arrays.asList(TWO, TWO, ONE, ONE));
        List<Coin> change = coinsManager.getChange(new BigDecimal("5.0"));

        assertThat(change).containsExactly(TWO, TWO, ONE);
    }

    @Test
    public void coins_manager_should_return_correct_change_amount_with_lower_denominations() {
        coinsManager.putCoins(Arrays.asList(TWO, ONE, ONE, HALVE, FIFTH, FIFTH, TENTH, TENTH));
        List<Coin> change = coinsManager.getChange(new BigDecimal("5.0"));

        assertThat(change).containsExactly(TWO, ONE, ONE, HALVE, FIFTH, FIFTH, TENTH);
    }

    @Test
    public void coins_manager_should_throw_correct_exception_when_it_has_not_enough_coins_for_the_change() {
        coinsManager.putCoins(Arrays.asList(TWO, ONE));
        Throwable thrown = catchThrowable(() -> coinsManager.getChange(new BigDecimal("5.0")));

        assertThat(thrown).isExactlyInstanceOf(NoMoneyForTheChange.class);
    }

}
