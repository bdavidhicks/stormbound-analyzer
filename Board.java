package stormboundanalyzer;

import java.util.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Position {
  int row, col;

  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public boolean equals(Position b) {
    return this.row == b.row && this.col == b.col;
  }
  public int getRow() {return this.row;}
  public int getCol() {return this.col;}
  public int moveRow(int amount) {this.row += amount; return this.row;}
  public int moveCol(int amount) {this.col += amount; return this.col;}
}

class Tile {
  Player owner;
  Summon summon;
  Position position;

  public Tile(Player owner, Summon summon, int row, int col) {
    this.owner = owner;
    this.summon = summon;
    this.position = new Position(row, col);
  }
  public Player getOwner() {return this.owner;}
  public Summon getSummon() {return this.summon;}
  public Position getPosition() {return this.position;}

  public String toString() {
    if (this.owner.isOpponent()) {
      return "V" + this.summon.toString();
    } else {
      return "^" + this.summon.toString();
    }
  }
}

public class Board {

  int rows, cols;
  List<Tile> tiles;

  public Board(int rows, int cols) throws Exception {
    if (rows > 0 && cols > 0) {
      this.rows = rows;
      this.cols = cols;
      this.tiles = new ArrayList<Tile>();
    } else {
      throw new Exception("Rows and cols must be greater than 0");
    }
  }
  public int getRows() {return this.rows;}
  public int getCols() {return this.cols;}
  public Tile getTileAt(int row, int col) {
    return this.tiles.stream()
        .filter(tile -> new Position(row, col).equals(tile.getPosition()))
        .findAny().orElse(null);
  }
  public boolean isTileEmptyAt(int row, int col) {
    return this.tiles.stream()
        .map(Tile::getPosition)
        .filter(pos -> pos.equals(new Position(row, col)))
        .collect(Collectors.toList()).size() == 0;
  }

    // def get_bordering_list(self, position):
    //     result = []
    //     if position.row + 1 < self.rows:
    //         result.append(self.spaces[position.row + 1][position.col])
    //     if position.col + 1 < self.cols:
    //         result.append(self.spaces[position.row][position.col + 1])
    //     if position.row - 1 >= 0:
    //         result.append(self.spaces[position.row - 1][position.col])
    //     if position.col - 1 >= 0:
    //         result.append(self.spaces[position.row][position.col - 1])
    //     return result
    //
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

    public boolean summon(Summon card, Player player, int row, int col) {
      if (this.isTileEmptyAt(row, col)) { //&& player.is_behind_front_line(position):
        this.tiles.add(new Tile(player, card, row, col));
        return true;
      } else {
        return false;
      }
    }

    public String toString() {
      StringBuilder result = new StringBuilder();
      for (int row = 0; row < this.rows; row++) {
        for (int col = 0; col < this.cols; col++) {
          result.append((this.isTileEmptyAt(row, col)) ? "   " : this.getTileAt(row, col).toString());
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
