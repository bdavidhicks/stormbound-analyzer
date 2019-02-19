package stormboundanalyzer;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

  int rows, cols;
  Game game;
  List<Tile> tiles;

  public Board(int rows, int cols, Game game) throws Exception {
    if (rows > 0 && cols > 0) {
      this.rows = rows;
      this.cols = cols;
      this.game = game;
      this.tiles = new ArrayList<Tile>();
    } else {
      throw new Exception("Rows and cols must be greater than 0");
    }
  }
  public int getRows() {return this.rows;}
  public int getCols() {return this.cols;}
  public Tile getTileAt(Position position) {
    return this.tiles.stream()
      .filter(tile -> position.equals(tile.getPosition()))
      .findAny().orElse(null);
  }
  public boolean isTileEmptyAt(Position position) {
    return this.tiles.stream()
      .map(Tile::getPosition)
      .filter(pos -> pos.equals(position))
      .collect(Collectors.toList()).size() == 0;
  }

  public void remove(Tile tile) {
    this.tiles.remove(tile);
  }

  public Position getPositionInFront(Position position, Player player) {
    if (player.isOpponent() && position.getRow() < this.rows - 1) {
      return new Position(position.getRow() + 1, position.getCol());
    } else if (!player.isOpponent() && position.getRow() > 0) {
      return new Position(position.getRow() - 1, position.getCol());
    }
    return null;
  }

  public Position getPositionEast(Position position) {
    if (position.getCol() < this.cols - 1) {
      return new Position(position.getRow(), position.getCol() + 1);
    }
    return null;
  }

  public Position getPositionWest(Position position) {
    if (position.getCol() > 0) {
      return new Position(position.getRow(), position.getCol() - 1);
    }
    return null;
  }

  public List<Position> getBorderingList(Position position) {
    Position north = new Position(position.getRow() - 1, position.getCol());
    Position east = new Position(position.getRow(), position.getCol() + 1);
    Position south = new Position(position.getRow() + 1, position.getCol());
    Position west = new Position(position.getRow(), position.getCol() - 1);
    List<Position> result = this.tiles.stream()
      .filter(t ->
        t.getPosition().equals(north) ||
        t.getPosition().equals(east) ||
        t.getPosition().equals(south) ||
        t.getPosition().equals(west))
      .map(t -> t.getPosition())
      .collect(Collectors.toList());
    return result;
  }
    // def get_bordering_nesw(self, position):
    //     result = {
    //         'north': None,
    //         'east': None,
    //         'south': None,
    //         'west': None
    //     }
    //     result['north'] = self.spaces[position.row - 1][position.col] \
    //         if position.row - 1 < self.rows else None
    //     result['east'] = self.spaces[position.row][position.col + 1] \
    //         if position.col + 1 < self.cols else None
    //     result['south'] = self.spaces[position.row + 1][position.col] \
    //         if position.row + 1 < self.rows else None
    //     result['west'] = self.spaces[position.row][position.col - 1] \
    //         if position.col - 1 >= 0 else None
    //     return result
    //
    // def get_surrounding(self, position):
    //     result = []
    //     for row_adj in range(-1, 2):
    //         for col_adj in range(-1, 2):
    //             if row_adj != 0 or col_adj != 0:
    //                 new_row = position.row + row_adj
    //                 new_col = position.col + col_adj
    //                 if new_row >= 0 and new_row < self.rows and \
    //                         new_col >= 0 and new_col < self.cols:
    //                     result.append(self.spaces[new_row][new_col])
    //     return result

    public void startTurn(Player player) {
      if (player.isOpponent()) {
        for (int row = this.rows - 1; row >= 0; row--) {
          for (int col = this.cols - 1; col >= 0; col--) {
            Position position = new Position(row, col);
            Summon summon = this.tiles.stream()
              .filter(t -> t.getPosition().equals(position) && t.getSummon() instanceof Unit && t.getOwner() == player)
              .map(t -> t.getSummon())
              .findAny().orElse(null);
            if (summon != null) {
              Unit unit = (Unit)summon;
              if (unit.isFrozen()) {
                unit.unfreeze();
              } else {
                unit.attackOrMoveAhead(game, player, position);
              }
            }
          }
        }
      } else {
        for (int row = 0; row < this.rows; row++) {
          for (int col = 0; col < this.cols; col++) {
            Position position = new Position(row, col);
            Summon summon = this.tiles.stream()
              .filter(t -> t.getPosition().equals(position) && t.getSummon() instanceof Unit && t.getOwner() == player)
              .map(t -> t.getSummon())
              .findAny().orElse(null);
            if (summon != null) {
              Unit unit = (Unit)summon;
              if (unit.isFrozen()) {
                unit.unfreeze();
              } else {
                unit.attackOrMoveAhead(game, player, position);
              }
            }
          }
        }
      }
    }

    public void summon(Summon card, Player player, Position position) {
      if (this.isTileEmptyAt(position)) { //&& player.is_behind_front_line(position):
        this.tiles.add(new Tile(player, card, position));
      }
    }

    public String toString() {
      StringBuilder result = new StringBuilder();
      for (int row = 0; row < this.rows; row++) {
        for (int col = 0; col < this.cols; col++) {
          Position position = new Position(row, col);
          result.append((this.isTileEmptyAt(position)) ? "   " : this.getTileAt(position).toString());
          if (col < this.cols - 1) { result.append("|"); }
        }
        if (row < this.rows - 1) {
          result.append(String.format("%n"));
          result.append(Stream.generate(() -> "-").limit(this.cols*4).collect(Collectors.joining("")));
          result.append(String.format("%n"));
        }
      }
      return result.toString();
    }
}
