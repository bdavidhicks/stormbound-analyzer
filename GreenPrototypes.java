package stormboundanalyzer;

import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GreenPrototypes extends Construct {
  private GreenPrototypes(String name, String text, int level, Faction faction, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, cost, strength, movement);
  }

  public GreenPrototypes(int level) throws Exception {
    super(
      "Green Prototypes",
      String.format("On death, give %d strength to a random bordering ENEMY unit", level),
      level,
      Faction.NEUTRAL,
      1,
      level,
      1
    );
  }

  public void onDeath(Game game, Player player, Position position) {
    // give a random boarding ENEMY unit strength equal to level
    Board board = game.getBoard();
    List<Position> bordering = board.getBorderingList(position);
    List<Summon> targets = bordering.stream()
      .filter(b -> !board.isTileEmptyAt(b) &&
        board.getTileAt(b).getOwner() != player &&
        board.getTileAt(b).getSummon() instanceof Unit &&
        board.getTileAt(b).getSummon().getCurrentStrength() > 0)
      .map(b -> board.getTileAt(b).getSummon())
      .collect(Collectors.toList());
    if (targets.size() > 0) {
      Random rand = new Random();
      Summon choice = targets.get(rand.nextInt(targets.size()));
      choice.addStrength(this.level);
    }
  }
}
