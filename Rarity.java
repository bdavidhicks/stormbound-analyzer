package com.stormboundanalyzer;

public enum Rarity {
  COMMON("Common"),
  RARE("Rare"),
  EPIC("Epic"),
  LEGENDARY("Legendary");

  private String name;

  public String getName() {
    return this.name;
  }

  public String toString() {
    return this.name;
  }

  Rarity(String name) {
    this.name = name;
  }
}
