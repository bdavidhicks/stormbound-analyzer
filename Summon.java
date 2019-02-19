package stormboundanalyzer;

public class Summon extends Card {
  int strength, currentStrength;
  boolean poisoned, frozen;

  public Summon(String name, String text, int level, Faction faction, int cost, int strength) throws Exception {
    super(name, text, level, faction, cost);
    this.strength = strength;
    this.currentStrength = strength;
    this.frozen = false;
    this.poisoned = false;
  }

  public boolean canPlay(Game game, Player player, Position position) {
    if (super.canPlay(game, player, position) &&
        game.getBoard().isTileEmptyAt(position) ) { //&&
        //player.is_behind_front_line(position)) {
      return true;
    } else {
      return false;
    }
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      game.getBoard().summon(this, player, position);
    }
  }

  public boolean isFrozen() { return this.frozen; }
  public void unfreeze() {this.frozen = false;}
  public boolean isAlive() { return this.currentStrength > 0; }
  public int getCurrentStrength() { return this.currentStrength; }
  public boolean isPoisoned() { return this.poisoned; }
  public void takeDamage(int damage) { this.currentStrength -= damage; }
  public void addStrength(int amount) { this.currentStrength += amount; }
  public void onTurnBegin(Game game, Player player, Position position) { }
  public void onDeath(Game game, Player player, Position position) { }
  public String toString() { return "" + this.currentStrength; }
}
