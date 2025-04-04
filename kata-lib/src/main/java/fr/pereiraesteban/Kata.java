package fr.pereiraesteban;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Kata {
  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private final ArrayList<TransformationRule> numberRules = new ArrayList<>();
    private final ArrayList<TransformationRule> digitRules = new ArrayList<>();
    private Builder() {
      //Hide default constructor
    }

    public Builder registerNumberRule(Predicate<Integer> matchOn, String matchingValue) {
      Objects.requireNonNull(matchOn);
      Objects.requireNonNull(matchingValue);

      var rule = new TransformationRule(matchOn, (__ -> matchingValue));
      numberRules.add(rule);
      return this;
    }

    public Builder registerDigitRule(Predicate<Integer> matchOn, Function<Integer, String> transformation) {
      Objects.requireNonNull(matchOn);
      Objects.requireNonNull(transformation);

      var rule = new TransformationRule(matchOn, transformation);
      digitRules.add(rule);
      return this;
    }

    public Kata build() {
      return new Kata(numberRules, digitRules);
    }
  }

  private final ArrayList<TransformationRule> numberRules;
  private final ArrayList<TransformationRule> digitRules;

  private Kata(ArrayList<TransformationRule> numberRules, ArrayList<TransformationRule> digitRules) {
    this.numberRules = numberRules;
    this.digitRules = digitRules;
  }

  public String transform(int input) {
    if (input < 0 || input > 100) {
      throw new IllegalArgumentException("Invalid range (range is [0-100]");
    }
    var sb = new StringBuilder();
    applyNumberRules(sb, input);
    applyDigitRules(sb, input);

    if (sb.isEmpty()) {
      sb.append(input);
    }

    return sb.toString();
  }

  private void applyNumberRules(StringBuilder sb, int input) {
    for (var rule : numberRules) {
      if (rule.canMap(input)) {
        var value = rule.map(input);
        sb.append(value);
      }
    }
  }

  private void applyDigitRules(StringBuilder sb, int input) {
    var inputAsString = input + "";
    for (var character : inputAsString.chars().toArray()) {
      for (var rule : digitRules) {
        if (rule.canMap(character)) {
          sb.append(rule.map(character));
        }
      }
    }
  }
}
