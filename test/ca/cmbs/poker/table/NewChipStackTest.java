package ca.cmbs.poker.table;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewChipStackTest {

    @Test
    public void divide_100_by_2() {
        ChipStack stack = ChipStack.of(BigDecimal.valueOf(100.0));
        List<ChipStack> expectation = new LinkedList<>();
        expectation.add(ChipStack.of(BigDecimal.valueOf(50)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(50)));

        List<ChipStack> results = stack.divide(2);
        assertEquals(expectation, results);
    }

    @Test
    public void divide_100_by_5() {
        ChipStack stack = ChipStack.of(BigDecimal.valueOf(100.0));
        List<ChipStack> expectation = new LinkedList<>();
        expectation.add(ChipStack.of(BigDecimal.valueOf(20)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(20)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(20)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(20)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(20)));

        List<ChipStack> results = stack.divide(5);
        assertEquals(expectation, results);
    }

    @Test
    public void divide_100_by_1() {
        ChipStack stack = ChipStack.of(BigDecimal.valueOf(100.0));
        List<ChipStack> expectation = new LinkedList<>();
        expectation.add(ChipStack.of(BigDecimal.valueOf(100.0)));

        List<ChipStack> results = stack.divide(1);
        assertEquals(expectation, results);
    }

    @Test
    public void divide_104_by_5() {
        ChipStack stack = ChipStack.of(BigDecimal.valueOf(104.0));
        List<ChipStack> expectation = new LinkedList<>();
        expectation.add(ChipStack.of(BigDecimal.valueOf(21)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(21)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(21)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(21)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(20)));

        List<ChipStack> results = stack.divide(5);
        assertEquals(expectation, results);
    }

    @Test
    public void divide_100_by_3() {
        ChipStack stack = ChipStack.of(BigDecimal.valueOf(100.0));
        List<ChipStack> expectation = new LinkedList<>();
        expectation.add(ChipStack.of(BigDecimal.valueOf(33)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(34)));
        expectation.add(ChipStack.of(BigDecimal.valueOf(33)));

        List<ChipStack> results = stack.divide(3);
        assertEquals(expectation, results);
    }

    @RepeatedTest(1000)
    public void divide_random_stacks_no_loss() {
        Random rand = new Random();

        ChipStack total = ChipStack.of(rand.nextDouble() * Math.abs(rand.nextInt()));
        int divisor = Math.abs(rand.nextInt(13)) + 1;

        List<ChipStack> results = total.divide(divisor);
        assertEquals(total, results.stream().reduce(ChipStack.of(BigDecimal.ZERO), ChipStack::add));
    }

    @Test
    public void divide_by_0_fails() {
        assertThrows(IllegalArgumentException.class, () -> ChipStack.of(10).divide(0));
    }


}
