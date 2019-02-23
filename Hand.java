package com.stormboundanalyzer;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Hand {
  List<Card> cards;
  int maxCards;

  public Hand(int maxCards) {
    this.cards = new ArrayList<Card>();
    this.maxCards = maxCards;
  }

  public List<Card> getCards() {return this.cards;}
  public void addCard(Card card) {
    if (this.cards.size() < maxCards) {
      this.cards.add(card);
    }
  }
  public void removeCard(Card card) {
    Card discardCard = this.cards.stream()
      .filter(c -> c.getName().equals(card.getName()))
      .findAny().orElse(null);
    if (discardCard != null) {
      this.cards.remove(discardCard);
    }
  }
  public boolean isEmpty() {return this.cards.size() == 0;}
  public int getNumCards() {return this.cards.size();}
  public int getMaxCards() {return this.maxCards;}
  public boolean isFull() {return this.cards.size() >= this.maxCards;}

  public String toString() {
    Collections.sort(this.cards);
    StringBuilder result = new StringBuilder();
    for (Card card: this.cards) {
      result.append(card.toString());
      result.append(String.format("%n"));
    }
    return result.toString();
  }
}
