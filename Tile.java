package stormboundanalyzer;

class Tile {
  Player owner;
  Summon summon;
  Position position;

  public Tile(Player owner, Summon summon, Position position) {
    this.owner = owner;
    this.summon = summon;
    this.position = position;
  }
  public Player getOwner() {return this.owner;}
  public Summon getSummon() {return this.summon;}
  public Position getPosition() {return this.position;}

  public String toString() {
    if (this.owner.isOpponent()) {
      return "v" + String.format("%-2s", this.summon.toString());
    } else {
      return "^" + String.format("%-2s", this.summon.toString());
    }
  }
}
