package fr.pereiraesteban;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

class TransformationRule<I, O> {
  private final Predicate<I> assertion;
  private final Function<I, O> strategy;
  public TransformationRule(Predicate<I> assertion, Function<I, O> strategy) {
    this.assertion = Objects.requireNonNull(assertion);
    this.strategy = Objects.requireNonNull(strategy);
  }

  public boolean canMap(I input) {
    return assertion.test(input);
  }

  public O map(I input) {
    return strategy.apply(input);
  }
}
