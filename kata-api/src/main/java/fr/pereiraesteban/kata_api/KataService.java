package fr.pereiraesteban.kata_api;

import fr.pereiraesteban.Kata;
import org.springframework.stereotype.Service;

@Service
public class KataService {
  private final Kata kata;

  public KataService() {
    this.kata = Kata.builder()
        .registerNumberRule(i -> (i % 3) == 0, "FOO")
        .registerNumberRule(i -> (i % 5) == 0, "BAR")
        .registerDigitRule(c -> c == '3', __ -> "FOO")
        .registerDigitRule(c -> c == '5', __ -> "BAR")
        .registerDigitRule(c -> c == '7', __ -> "QUIX")
        .build();
  }

  public String apply(int input) {
    return kata.transform(input);
  }
}
