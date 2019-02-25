package com.stormboundanalyzer;

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
    return position == null || this.tiles.stream()
      .map(Tile::getPosition)
      .filter(pos -> pos.equals(position))
      .collect(Collectors.toList()).size() == 0;
  }

  public void remove(Tile tile) {
    this.tiles.remove(tile);
  }

  public Position getPositionInFront(Player player, Position position) {
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
    List<Position> result = new ArrayList<Position>();
    if (position.getRow() - 1 >= 0) { result.add(new Position(position.getRow() - 1, position.getCol())); }
    if (position.getRow() + 1 < this.getRows()) { result.add(new Position(position.getRow() + 1, position.getCol())); }
    if (position.getCol() - 1 >= 0) { result.add(new Position(position.getRow(), position.getCol() - 1)); }
    if (position.getCol() + 1 < this.getCols()) { result.add(new Position(position.getRow(), position.getCol() + 1)); }
    return result;
  }

  public List<Position> getSurroundingList(Position position) {
    List<Position> surrounding = new ArrayList<Position>();
    for (int row = -1; row <= 1; row++) {
      for (int col = -1; col <= 1; col++) {
        if (row != 0 || col != 0) {
          int newRow = position.getRow() + row;
          int newCol = position.getCol() + col;
          if (newRow >= 0 && newRow < this.getRows() && newCol >= 0 && newCol < this.getCols()) {
            surrounding.add(new Position(position.getRow() + row, position.getCol() + col));
          }
        }
      }
    }
    return surrounding;
  }

  public List<Tile> getAllTargets() {
    List<Tile> targets = new ArrayList<Tile>(this.tiles);
    targets.add(new Tile(this.game.getTopPlayer(), this.game.getTopPlayer().getPlayerBase(), null));
    targets.add(new Tile(this.game.getBottomPlayer(), this.game.getBottomPlayer().getPlayerBase(), null));
    return targets;
  }

  public List<Tile> getRowTiles(int row) {
    return this.tiles.stream()
      .filter(t -> t.getPosition().getRow() == row)
      .collect(Collectors.toList());
  }

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
              unit.attack(game, player, position, this.getPositionInFront(player, position));
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
              unit.attack(game, player, position, this.getPositionInFront(player, position));
            }
          }
        }
      }
    }
  }

  public void summon(Summon card, Player player, Position position, boolean checkFrontLine) {
    if (this.isTileEmptyAt(position)) { //&& player.is_behind_front_line(position):
      this.tiles.add(new Tile(player, card, position));
    }
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("  ");
    for (int col = 0; col < this.cols; col++) {
      result.append(String.format(" %s  ", (char)('A' + col)));
    }
    result.append(String.format("%n"));
    for (int row = 0; row < this.rows; row++) {
      result.append(String.format("%d ", row));
      for (int col = 0; col < this.cols; col++) {
        Position position = new Position(row, col);
        result.append((this.isTileEmptyAt(position)) ? "   " : this.getTileAt(position).toString());
        if (col < this.cols - 1) { result.append("|"); }
      }
      if (row < this.rows - 1) {
        result.append(String.format("%n"));
        result.append("  ");
        result.append(Stream.generate(() -> "-").limit(this.cols*4).collect(Collectors.joining("")));
        result.append(String.format("%n"));
      }
    }
    return result.toString();
  }
}
