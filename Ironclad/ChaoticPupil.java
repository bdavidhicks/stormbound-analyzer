package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ChaoticPupil extends Rodent {
  private ChaoticPupil(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public ChaoticPupil(Integer level) throws Exception {
    super(
      "Chaotic Pupil",
      String.format("On death, deal %d damage spread randomly among all surrounding enemies", calcDamage(level.intValue())),
      level.intValue(),
      Faction.IRONCLAD_UNION,
      Rarity.COMMON,
      4,
      1,
      2
    );
  }

  public ChaoticPupil copyCard() throws Exception {
    return new ChaoticPupil(this.getLevel());
  }

  private static int calcDamage(int level) {
    return level + 1;
  }

  public void onDeath(Game game, Player player, Position position) {
    super.onDeath(game, player, position);
    // deal damage spread randomly among all surrounding enemies
    Board board = game.getBoard();
    List<Tile> targets = board.getSurroundingTargets(position).stream()
      .filter(t -> !t.getOwner().equals(player) &&
        t.getSummon().isAlive())
      .collect(Collectors.toList());
    int numShots = calcDamage(this.getLevel());
    while (numShots > 0 && targets.size() > 0) {
      Tile choice = Choice.chooseOne(targets);
      choice.getSummon().takeDamage(game, choice.getOwner(), choice.getPosition(), 1);
      targets = board.getSurroundingTargets(position).stream()
        .filter(t -> !t.getOwner().equals(player) &&
          t.getSummon().isAlive())
        .collect(Collectors.toList());
      numShots--;
    }
  }
}
