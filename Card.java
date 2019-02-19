package stormboundanalyzer;

public class Card {
  String name, text;
  int level, cost;
  Faction faction;

  public Card(String name, String text, int level, Faction faction, int cost) throws Exception {
    this.name = name;
    this.text = text;
    if (level < 1 || level > 5) {
      throw new Exception("Card level must be between 1 and 5");
    }
    this.level = level;
    this.faction = faction;
    if (cost < 0) {
      throw new Exception("Card cost must be greater than or equal to 0");
    }
    this.cost = cost;
  }

  public boolean canPlay(Game game, Player player, Position position) {
    if (player.getCurrentMana() < this.cost) {
      return false;
    } else {
      return true;
    }
  }

  public void play(Game game, Player player, Position position) {
    if (this.canPlay(game, player, position)) {
      player.spendMana(this.cost);
      // TODO: remove card from hand
    }
  }

  public String getName() { return this.name; }
  public int getLevel() { return this.level; }
}

// class Spell(Card):
//   def __init__(self, name, text, level, faction, cost, has_target,
//                valid_targets):
//       super(Summon, self).__init__(name, text, level, faction, cost)
//       self.has_target = has_target
//       self.valid_targets = valid_targets
//
//
//
//
// class Structure(Summon):
//   def __init__(self, name, text, level, faction, cost, strength):
//       super(Structure, self).__init__(name, text, level, faction, cost,
//                                       strength)
//
//
//
// class Frostling(Unit):
//   def __init__(self, name, text, level, faction, cost, strength, movement):
//       super(Frostling, self).__init__(
//           name, text, level, faction, cost, strength, movement)
