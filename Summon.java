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

  public boolean canPlay(Board board, Player player, int row, int col) {
    if (super.canPlay(board, player, row, col) &&
        board.isTileEmptyAt(row, col) ) { //&&
        //player.is_behind_front_line(position)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean play(Board board, Player player, int row, int col) {
    if (this.canPlay(board, player, row, col)) {
      super.play(board, player, row, col);
      return board.summon(this, player, row, col);
    } else {
      return false;
    }
  }

  public boolean isFrozen() { return this.frozen; }
  public boolean isAlive() { return this.currentStrength > 0; }
  public boolean isPoisoned() { return this.poisoned; }
  public void onTurnBegin(Board board, Player player, int row, int col) { }
  public void onDeath(Board board, Player player, int row, int col) { }
  public String toString() { return "" + this.currentStrength; }
}
