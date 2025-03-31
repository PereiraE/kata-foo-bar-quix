package fr.pereiraesteban;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestKata {
  @Test
  void testInvalidRangeRaiseException() {
    var kata = Kata.builder().build();
    assertThrows(IllegalArgumentException.class, () -> kata.transform(-4));
    assertThrows(IllegalArgumentException.class, () -> kata.transform(101));
  }

  @Test
  void testNoRulesMatchReturnsInputAsString() {
    var kata = Kata.builder().build();
    assertEquals("1", kata.transform(1));
    assertEquals("2", kata.transform(2));
  }

  @Test
  void testInputDivisibleBy3ReturnsFoo() {
    var kata = Kata.builder()
        .registerNumberRule(input -> (input % 3) == 0, "FOO")
        .build();
    assertEquals("FOO", kata.transform(12));
    assertEquals("FOO", kata.transform(9));
  }

  @Test
  void testInputDivisibleBy5ReturnsBar() {
    var kata = Kata.builder()
        .registerNumberRule(input -> (input % 5) == 0, "BAR")
        .build();
    assertEquals("BAR", kata.transform(20));
    assertEquals("BAR", kata.transform(25));
  }

  @Test
  void testInputContains7ReturnsQuix() {
    var kata = Kata.builder()
        .registerDigitRule(c -> c == '7', __ -> "QUIX")
        .build();
    assertEquals("QUIX", kata.transform(7));
  }

  static final List<Arguments> arguments = List.of(
      Arguments.arguments("1", 1),
      Arguments.arguments("FOOFOO", 3),
      Arguments.arguments("BARBAR", 5),
      Arguments.arguments("QUIX", 7),
      Arguments.arguments("FOO", 9),
      Arguments.arguments("FOOBAR", 51),
      Arguments.arguments("BARFOO", 53),
      Arguments.arguments("FOOFOOFOO", 33),
      Arguments.arguments("FOOBARBAR", 15)
  );

  @ParameterizedTest
  @FieldSource("arguments")
  void testNumberRuleAndDigitRuleMatch(String expectedResult, int input) {
    var kata = Kata.builder()
        .registerNumberRule(i -> (i % 3) == 0, "FOO")
        .registerNumberRule(i -> (i % 5) == 0, "BAR")
        .registerDigitRule(c -> c == '3', __ -> "FOO")
        .registerDigitRule(c -> c == '5', __ -> "BAR")
        .registerDigitRule(c -> c == '7', __ -> "QUIX")
        .build();

    assertEquals(expectedResult, kata.transform(input));
  }
}
