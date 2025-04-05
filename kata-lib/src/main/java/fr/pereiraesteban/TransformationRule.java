package fr.pereiraesteban;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

class TransformationRule {
  private final Predicate<Integer> assertion;
  private final Function<Integer, String> strategy;
  public TransformationRule(Predicate<Integer> assertion, Function<Integer, String> strategy) {
    this.assertion = Objects.requireNonNull(assertion);
    this.strategy = Objects.requireNonNull(strategy);
  }

  public boolean canMap(Integer input) {
    return assertion.test(input);
  }

  public String map(Integer input) {
    return strategy.apply(input);
  }
}
