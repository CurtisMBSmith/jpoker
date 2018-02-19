package ca.cmbs.util.function;

@FunctionalInterface
public interface Procedure {

    void execute();

    default void doNothing() {
    }
}
