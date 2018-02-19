package ca.cmbs.poker.table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class ChipStack implements Comparable<ChipStack> {

    private static final int DEFAULT_SCALE = 0;
    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;

    private final BigDecimal chips;

    private ChipStack(final BigDecimal chips) {
        this.chips = chips;
    }

    public static ChipStack of(final BigDecimal chips) {
        return new ChipStack(chips);
    }

    public static ChipStack of(final double chips) {
        return new ChipStack(BigDecimal.valueOf(chips).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING));
    }

    public static ChipStack zero() {
        return of(0.0);
    }

    public ChipStack add(final ChipStack amount) {
        return new ChipStack(chips.add(amount.chips));
    }

    public ChipStack remove(final ChipStack amount) {
        return new ChipStack(chips.subtract(amount.chips));
    }

    public ChipStack difference(final ChipStack amount) {
        return new ChipStack(chips.subtract(amount.chips));
    }

    public List<ChipStack> divide(final int divisor) {
        if (divisor <= 0) {
            throw new IllegalArgumentException("Divisor must be positive.");
        }


        List<ChipStack> results = new LinkedList<>();
        BigDecimal remainder = chips;
        for (int i = divisor; i > 0; i--) {
            BigDecimal divisionResult = remainder.divide(BigDecimal.valueOf(i), DEFAULT_SCALE, DEFAULT_ROUNDING);
            remainder = remainder.subtract(divisionResult);
            results.add(ChipStack.of(divisionResult));
        }

        return results;
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof ChipStack && this.chips.compareTo(((ChipStack) other).chips) == 0;
    }

    @Override
    public int hashCode() {
        return this.chips.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s chips", chips);
    }

    @Override
    public int compareTo(final ChipStack chipStack) {
        return this.chips.compareTo(chipStack.chips);
    }

    public boolean lessThan(final ChipStack amount) {
        return this.compareTo(amount) < 0;
    }

    public boolean lessThanOrEqualTo(final ChipStack amount) {
        return this.compareTo(amount) <= 0;
    }

    public boolean greaterThan(final ChipStack amount) {
        return this.compareTo(amount) > 0;
    }

    public boolean greaterThanOrEqualTo(final ChipStack amount) {
        return this.compareTo(amount) >= 0;
    }

    public double doubleValue() {
        return this.chips.doubleValue();
    }
}
