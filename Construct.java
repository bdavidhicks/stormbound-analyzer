package stormboundanalyzer;

class Construct extends Unit {
  public Construct(String name, String text, int level, Faction faction, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, cost, strength, movement);
  }

  public Construct copyCard() throws Exception {
    return new Construct(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getCost(), this.getStartingStrength(), this.getMovement());
  }
}
