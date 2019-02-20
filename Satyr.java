package stormboundanalyzer;

class Satyr extends Unit {
  public Satyr(String name, String text, int level, Faction faction, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, cost, strength, movement);
  }

  public Satyr copyCard() throws Exception {
    return new Satyr(this.getName(), this.getText(), this.getLevel(), this.getFaction(), this.getCost(), this.getStartingStrength(), this.getMovement());
  }
}
