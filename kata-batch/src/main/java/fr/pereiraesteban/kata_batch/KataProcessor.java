package fr.pereiraesteban.kata_batch;

import fr.pereiraesteban.Kata;
import org.springframework.batch.item.ItemProcessor;

public class KataProcessor implements ItemProcessor<Integer, String> {
  private final Kata kata = Kata.builder()
      .registerNumberRule(i -> (i % 3) == 0, __ ->"FOO")
      .registerNumberRule(i -> (i % 5) == 0, __ ->"BAR")
      .registerDigitRule(c -> c == '3', __ -> "FOO")
      .registerDigitRule(c -> c == '5', __ -> "BAR")
      .registerDigitRule(c -> c == '7', __ -> "QUIX")
      .build();

  @Override
  public String process(Integer item) {
    var value = kata.transform(item);
    return String.format("%-3s %s", item, value);
  }
}
