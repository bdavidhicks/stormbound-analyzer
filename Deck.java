package com.stormboundanalyzer;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.lang.Class;
import java.lang.reflect.Constructor;

public class Deck {
  List<Card> cards;
  String name;

  public Deck(File file) throws Exception {
    this.name = file.getName().substring(0, file.getName().lastIndexOf('.'));
    this.cards = new ArrayList<Card>();
    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;
    while ((line = br.readLine()) != null) {
      String[] items = line.split(",");
      String[] nameStrings = Arrays.copyOfRange(items, 0, items.length - 1);
      String cardClassName = this.getCardClassName(String.join("", nameStrings));
      int cardLevel = Integer.parseInt(items[items.length - 1]);
      try {
        Class<?> clazz = Class.forName(cardClassName);
        Constructor<?> constructor = clazz.getConstructor(Integer.class);
        Card instance = (Card)constructor.newInstance(cardLevel);
        this.cards.add(instance);
      } catch (ClassNotFoundException cnfe) {
        System.out.println(cnfe.toString());
      }
    }
    if (this.cards.size() != 12) {
      throw new Exception("Invalid deck, more or less than 12 cards");
    } else if (this.getFactions().size() == 0) {
      throw new Exception("Invalid deck, unable to determine Faction");
    } else if (this.getFactions().size() == 2 && !this.getFactions().contains(Faction.NEUTRAL)) {
      throw new Exception("Invalid deck, 2 Factions used in deck (other than neutral)");
    } else if (this.getFactions().size() > 2) {
      throw new Exception("Invalid deck, too many Factions used in deck");
    }
  }

  public List<Card> getCards() {return this.cards;}
  public String getName() {return this.name;}

  public String getCardClassName(String cardName) {
    String[] words = cardName.split("\\s");
    StringBuilder result = new StringBuilder();
    result.append("com.stormboundanalyzer.");
    for (int w = 0; w < words.length; w++) {
      String word = words[w].replaceAll("[^a-zA-Z]", "");
      result.append(word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase());
    }
    return result.toString();
  }

  public List<Faction> getFactions() {
    Set<Faction> factions = new HashSet<Faction>();
    this.cards.stream()
      .map(c -> factions.add(c.getFaction()))
      .collect(Collectors.toList());
    return new ArrayList<Faction>(factions);
  }

  public String toString() {
    return this.getName();
  }
}
