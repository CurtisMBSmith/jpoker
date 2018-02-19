package ca.cmbs.util;

import java.util.function.Predicate;

public class ValidationUtils {

    public static Predicate<String> IS_NUMERIC_VALIDATOR = (arg) -> {
        try {
            Double.parseDouble(arg);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    };

    private ValidationUtils() {
        throw new RuntimeException("Utility - Do not instantiate.");
    }
}
