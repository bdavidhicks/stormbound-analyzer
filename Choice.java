package com.stormboundanalyzer;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Choice {

  public static <T> List<T> chooseMany(List<T> choices, int numberToChoose) {
    if (numberToChoose >= choices.size()) {
      return choices;
    }
    Random rand = new Random();
    List<T> chosen = new ArrayList<T>();
    while (chosen.size() < numberToChoose) {
      T choice = choices.get(rand.nextInt(choices.size()));
      while (chosen.contains(choice)) {
        choice = choices.get(rand.nextInt(choices.size()));
      }
      chosen.add(choice);
    }
    return chosen;
  }

  public static <T> T chooseOne(List<T> choices) {
    if (choices.size() == 0) {
      return null;
    } else {
      return Choice.chooseMany(choices, 1).get(0);
    }
  }
}
