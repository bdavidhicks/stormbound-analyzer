package stormboundanalyzer;

public class Player {
  int currentMana, level;
  boolean isOpponent, goesFirst;
  Faction faction;
  String name;
  Base base;
  public Player(Faction faction, boolean isOpponent, int level, boolean goesFirst) throws Exception {
    if (faction != Faction.NEUTRAL) {
      this.goesFirst = goesFirst;
      this.currentMana = (goesFirst) ? 3 : 4;
      this.isOpponent = isOpponent;
      this.faction = faction;
      this.level = level;
      this.name = (isOpponent) ? "Opp" : "You";
      this.base = new Base(level);
    } else {
      throw new Exception("Invalid Faction supplied to player constructor");
    }
  }
  public int getCurrentMana() {return this.currentMana;}
  public int getLevel() {return this.level;}
  public boolean goesFirst() {return this.goesFirst;}
  public boolean isOpponent() {return this.isOpponent;}
  public Faction getFaction() {return this.faction;}
  public String getname() {return this.name;}
  public Base getBase() {return this.base;}
  public int spendMana(int mana) {this.currentMana -= mana; return this.currentMana;}
  public String toString() {
    return String.format("%s - %s with %d health base", // with line at {front_line}'.format(
      this.name,
      this.faction.getName(),
      this.base.getHealth()
      //this.front_line
    );
  }
}

//
//   def calc_front_line(self, board):
//       rows = reversed(range(board.rows)) if self.is_opponent else \
//           range(board.rows)
//       for row in rows:
//           for space in board.spaces[row]:
//               if not space.is_empty() and space.owner == self:
//                   if self.is_opponent:
//                       return max(
//                           self.base_line, min(board.rows - 1, row + 1))
//                   else:
//                       return min(self.base_line, max(1, row))
//       return self.base_line
//
//   def reset_front_line(self):
//       self.front_line = self.base_line
//
//   def is_behind_front_line(self, position):
//       if self.is_opponent:
//           return position.row <= self.front_line
//       else:
//           return position.row >= self.front_line
//

//
//   def __str__(self):
//       return self.__repr__()