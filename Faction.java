package com.stormboundanalyzer;

public enum Faction {
  IRONCLAD_UNION("Ironclad Union"),
  NEUTRAL("Neutral"),
  SWARM_OF_THE_EAST("Swarm of the East"),
  TRIBES_OF_SHADOWFEN("Tribes of Shadowfen"),
  WINTER_PACT("Winter Pact");

  private String name;

  public String getName() {
    return this.name;
  }

  public String toString() {
    return this.name;
  }

  Faction(String name) {
    this.name = name;
  }
}
