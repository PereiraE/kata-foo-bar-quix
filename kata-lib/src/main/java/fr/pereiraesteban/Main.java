package fr.pereiraesteban;

import java.util.List;

public class Main {

  public static void main(String[] args) {
    var input = List.of(1, 3, 5, 7, 9, 51, 53, 33, 33, 15);
    var expectedResult = List.of(
        "1",
        "FOOFOO",
        "BARBAR",
        "QUIX",
        "FOO",
        "FOOBAR",
        "BARFOO",
        "FOOFOOFOO",
        "FOOFOOFOO",
        "FOOBARBAR"
    );
  }
}