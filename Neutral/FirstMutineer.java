package com.stormboundanalyzer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FirstMutineer extends Pirate {
  private FirstMutineer(String name, String text, int level, Faction faction, Rarity rarity, int cost, int strength, int movement) throws Exception {
    super(name, text, level, faction, rarity, cost, strength, movement);
  }

  public FirstMutineer(Integer level) throws Exception {
    super(
      "First Mutineer",
      "On play, discard a random non-Pirate card from your hand",
      level.intValue(),
      Faction.NEUTRAL,
      Rarity.COMMON,
      3,
      level.intValue(),
      2
    );
  }

  public FirstMutineer copyCard() throws Exception {
    return new FirstMutineer(this.getLevel());
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      super.play(game, player, position);
      // Discard a non-Pirate card at random
      List<Card> nonPirateCards = player.getHand().getCards().stream()
        .filter(c -> !(c instanceof Pirate))
        .collect(Collectors.toList());
      if (nonPirateCards.size() > 0) {
        Card choice = Choice.chooseOne(nonPirateCards);
        player.discardCard(choice, false);
      }
    }
  }

}
