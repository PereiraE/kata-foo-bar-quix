package fr.pereiraesteban;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Kata {
  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private final ArrayList<TransformationRule<Integer, String>> numberRules = new ArrayList<>();
    private final ArrayList<TransformationRule<Integer, String>> digitRules = new ArrayList<>();
    private Function<Integer, String> defaultBehavior;

    private Builder() {
      //Hide default constructor
    }

    public Builder registerNumberRule(Predicate<Integer> matchOn, Function<Integer, String> transformation) {
      Objects.requireNonNull(matchOn);
      Objects.requireNonNull(transformation);

      var rule = new TransformationRule<>(matchOn, transformation);
      numberRules.add(rule);
      return this;
    }

    public Builder registerDigitRule(Predicate<Integer> matchOn, Function<Integer, String> transformation) {
      Objects.requireNonNull(matchOn);
      Objects.requireNonNull(transformation);

      var rule = new TransformationRule<>(matchOn, transformation);
      digitRules.add(rule);
      return this;
    }

    public Builder registerDefaultBehavior(Function<Integer, String> defaultBehavior) {
      this.defaultBehavior = Objects.requireNonNull(defaultBehavior);
      return this;
    }

    public Kata build() {
      return new Kata(numberRules, digitRules, defaultBehavior);
    }
  }

  private final ArrayList<TransformationRule<Integer, String>> numberRules;
  private final ArrayList<TransformationRule<Integer, String>> digitRules;
  private final Function<Integer, String> defaultBehavior;

  private Kata(
      ArrayList<TransformationRule<Integer, String>> numberRules,
      ArrayList<TransformationRule<Integer, String>> digitRules,
      Function<Integer, String> defaultBehavior
  ) {
    this.numberRules = numberRules;
    this.digitRules = digitRules;
    this.defaultBehavior = defaultBehavior;
  }

  public String transform(int input) {
    if (input < 0 || input > 100) {
      throw new IllegalArgumentException("Invalid range (range is [0-100]");
    }
    var sb = new StringBuilder();

    numberRules.stream().filter(r -> r.canMap(input)).map(r -> r.map(input)).forEach(sb::append);
    var inputAsString = input + "";
    inputAsString.chars()
        .mapToObj(c -> digitRules.stream()
            .filter(r -> r.canMap(c))
            .map(r -> r.map(input))
            .collect(Collectors.joining()))
        .forEach(sb::append);

    if (sb.isEmpty() && defaultBehavior != null) {
      var defaultValue = defaultBehavior.apply(input);
      sb.append(defaultValue);
    }

    return sb.toString();
  }
}
