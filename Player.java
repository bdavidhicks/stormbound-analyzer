package com.stormboundanalyzer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
  int currentMana, level, frontLine;
  boolean isOpponent, goesFirst;
  Faction faction;
  String name;
  PlayerBase playerBase;
  Deck deck;
  Hand hand;
  public Player(Faction faction, boolean isOpponent, int baseStrength, boolean goesFirst, int frontLine) throws Exception {
    if (faction != Faction.NEUTRAL) {
      this.goesFirst = goesFirst;
      this.currentMana = (goesFirst) ? 3 : 4;
      this.isOpponent = isOpponent;
      this.faction = faction;
      this.level = level;
      this.name = (isOpponent) ? "Opp" : "You";
      this.playerBase = new PlayerBase(baseStrength);
      this.hand = new Hand(4);
      this.frontLine = frontLine;
    } else {
      throw new Exception("Invalid Faction supplied to Player constructor");
    }
  }
  public void setDeck(Deck deck) {
    this.deck = deck;
    this.hand = new Hand(4);
  }
  public void discardCard(Card card, boolean replaceCard) {
    this.getHand().removeCard(card);
    if (replaceCard) {
      this.drawCard(card);
    }
  }
  public void drawCard(Card cardToIgnore) {
    this.getHand().addCard(this.getDeck().drawCard(this.getHand(), cardToIgnore));
  }
  public Deck getDeck() { return this.deck; }
  public Hand getHand() { return this.hand; }
  public int getCurrentMana() {return this.currentMana;}
  public int getLevel() {return this.level;}
  public boolean goesFirst() {return this.goesFirst;}
  public boolean isOpponent() {return this.isOpponent;}
  public Faction getFaction() {return this.faction;}
  public String getName() {return this.name;}
  public PlayerBase getPlayerBase() {return this.playerBase;}
  public int getFrontLine() {return this.frontLine;}
  public void setFrontLine(int frontLine) {this.frontLine = frontLine;}
  public void spendMana(int mana) {this.currentMana -= mana;}
  public void addMana(int mana) {this.currentMana += mana;}
  public void fillMana(int mana) {this.currentMana = mana;}
  public void fillHand() {
    if (this.getDeck() != null) {
      while (!this.getHand().isFull()) {
        this.drawCard(null);
      }
    }
  }
  public String toString() {
    return String.format("%s - %s with %d strength base and %d mana currently", // with line at {front_line}'.format(
      this.name,
      this.faction.getName(),
      this.playerBase.getCurrentStrength(),
      this.getCurrentMana()
      //this.front_line
    );
  }
}

//
//   def calc_front_line(self, board):
//       rows = reversed(range(board.rows)) if self.is_opponent else \
//           range(board.rows)
//       for row in rows:
//           for space in board.spaces[row]:
//               if not space.is_empty() and space.owner == self:
//                   if self.is_opponent:
//                       return max(
//                           self.base_line, min(board.rows - 1, row + 1))
//                   else:
//                       return min(self.base_line, max(1, row))
//       return self.base_line
//
//   def reset_front_line(self):
//       self.front_line = self.base_line
//
//   def is_behind_front_line(self, position):
//       if self.is_opponent:
//           return position.row <= self.front_line
//       else:
//           return position.row >= self.front_line
//

//
//   def __str__(self):
//       return self.__repr__()
