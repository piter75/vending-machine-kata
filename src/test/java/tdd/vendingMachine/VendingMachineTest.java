package tdd.vendingMachine;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class VendingMachineTest {

    @Test
    public void just_a_stupid_passing_test_to_ensure_that_tests_are_run() {
        ShelvesManager shelvesManager = new ShelvesManagerBuilder().build();
        Assertions.assertThat(new VendingMachine(shelvesManager)).isNotNull();
    }
}
