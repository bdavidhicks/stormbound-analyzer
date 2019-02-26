package com.stormboundanalyzer;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.lang.Class;

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

  public List<Position> getAnyPositionAsList() {
    List<Position> result =  new ArrayList<Position>();
    result.add(new Position(0,0));
    return result;
  }

  public List<Position> getAllPositions() {
    List<Position> positions = new ArrayList<Position>();
    for (int row = 0; row < this.getRows(); row++) {
      for (int col = 0; col < this.getCols(); col++) {
        positions.add(new Position(row, col));
      }
    }
    return positions;
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

  public List<Position> getBorderingPositions(Position position) {
    List<Position> result = new ArrayList<Position>();
    if (position.getRow() - 1 >= 0) { result.add(new Position(position.getRow() - 1, position.getCol())); }
    if (position.getRow() + 1 < this.getRows()) { result.add(new Position(position.getRow() + 1, position.getCol())); }
    if (position.getCol() - 1 >= 0) { result.add(new Position(position.getRow(), position.getCol() - 1)); }
    if (position.getCol() + 1 < this.getCols()) { result.add(new Position(position.getRow(), position.getCol() + 1)); }
    return result;
  }

  public List<Position> getBorderingEmptyPositions(Position position) {
    return this.getBorderingPositions(position).stream()
      .filter(p -> this.isTileEmptyAt(p))
      .collect(Collectors.toList());
  }

  public List<Position> getSurroundingPositions(Position position) {
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

  public List<Position> getSurroundingEmptyPositions(Position position) {
    return this.getSurroundingPositions(position).stream()
      .filter(p -> this.isTileEmptyAt(p))
      .collect(Collectors.toList());
  }

  public List<Position> getOpenBehindLinePositions(Player player) {
    return this.getAllPositions().stream()
      .filter(p -> this.isTileEmptyAt(p) &&
        this.isBehindFrontLine(player, p))
        .collect(Collectors.toList());
  }

  public List<Tile> getPlayerAllTargets(Player player) {
    List<Tile> targets = this.tiles.stream()
      .filter(t -> t.getOwner().equals(player) &&
        t.getSummon().isAlive())
      .collect(Collectors.toList());
    targets.add(new Tile(player, player.getPlayerBase(), null));
    return targets;
  }

  public List<Position> getOnBoardPlayerPositions(Player player) {
    return this.tiles.stream()
      .filter(t -> t.getOwner().equals(player) &&
        t.getSummon().isAlive())
      .map(Tile::getPosition)
      .collect(Collectors.toList());
  }

  public List<Position> getOnBoardPlayerTypePositions(Player player, Class<?> clazz) {
    return this.tiles.stream()
      .filter(t -> t.getOwner().equals(player) &&
        t.getSummon().isAlive() &&
        clazz.isInstance(t.getSummon()))
      .map(Tile::getPosition)
      .collect(Collectors.toList());
  }

  public List<Tile> getSurroundingPlayerTypeTargets(Player player, Position position, Class<?> clazz) {
    List<Position> surrounding = this.getSurroundingPositions(position);
    List<Tile> targets = this.tiles.stream()
      .filter(t -> surrounding.contains(t.getPosition()) &&
        t.getOwner().equals(player) &&
        t.getSummon().isAlive() &&
        clazz.isInstance(t.getSummon()))
      .collect(Collectors.toList());
    if (clazz.isInstance(player.getPlayerBase()) && (
        (position.getRow() == 0 && player.equals(this.game.getTopPlayer())) ||
        (position.getRow() == this.getRows() - 1 && player.equals(this.game.getBottomPlayer())) ) ) {
      targets.add(new Tile(player, player.getPlayerBase(), null));
    }
    return targets;
  }

  public List<Tile> getBorderingPlayerTypeTargets(Player player, Position position, Class<?> clazz) {
    List<Position> bordering = this.getBorderingPositions(position);
    List<Tile> targets = this.tiles.stream()
      .filter(t -> bordering.contains(t.getPosition()) &&
        t.getOwner().equals(player) &&
        t.getSummon().isAlive() &&
        clazz.isInstance(t.getSummon()))
      .collect(Collectors.toList());
    if (clazz.isInstance(player.getPlayerBase()) && (
        (position.getRow() == 0 && player.equals(this.game.getTopPlayer())) ||
        (position.getRow() == this.getRows() - 1 && player.equals(this.game.getBottomPlayer())) ) ) {
      targets.add(new Tile(player, player.getPlayerBase(), null));
    }
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
    this.setNewFrontLines();
  }

  public void summon(Summon card, Player player, Position position, boolean checkFrontLine) {
    if (this.isTileEmptyAt(position) && (!checkFrontLine || this.isBehindFrontLine(player, position))) {
      this.tiles.add(new Tile(player, card, position));
    }
  }

  public void setNewFrontLines() {
    game.getTopPlayer().setFrontLine(this.calcNewFrontLine(game.getTopPlayer()));
    game.getBottomPlayer().setFrontLine(this.calcNewFrontLine(game.getBottomPlayer()));
  }

  public int calcNewFrontLine(Player player) {
    int frontLine = player.getFrontLine();
    List<Tile> tiles = this.tiles.stream()
      .filter(t -> t.getOwner().equals(player) &&
        t.getSummon().isAlive())
      .collect(Collectors.toList());
    if (player.equals(game.getTopPlayer())) {
      if (player.equals(game.getActivePlayer())) {
        int calcLine = tiles.stream().map(t -> t.getPosition().getRow()).mapToInt(i -> i + 1).max().orElse(1);
        return Math.min(this.getRows() - 1, Math.max(calcLine, frontLine));
      } else {
        int calcLine = tiles.stream().map(t -> t.getPosition().getRow()).mapToInt(i -> i + 1).max().orElse(1);
        return Math.min(this.getRows() - 1, calcLine);
      }
    } else {
      if (player.equals(game.getActivePlayer())) {
        int calcLine = tiles.stream().map(t -> t.getPosition().getRow()).mapToInt(i -> i).min().orElse(this.getRows() - 1);
        return Math.max(1, Math.min(calcLine, frontLine));
      } else {
        int calcLine = tiles.stream().map(t -> t.getPosition().getRow()).mapToInt(i -> i).min().orElse(this.getRows() - 1);
        return Math.max(1, calcLine);
      }
    }
  }

  public boolean isBehindFrontLine(Player player, Position position) {
    int frontLine = player.getFrontLine();
    if (player.equals(game.getTopPlayer())) {
      return position.getRow() < frontLine;
    } else {
      return position.getRow() >= frontLine;
    }
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    int topFrontLine = game.getTopPlayer().getFrontLine();
    int bottomFrontLine = game.getBottomPlayer().getFrontLine();
    result.append("    ");
    for (int col = 0; col < this.cols; col++) {
      result.append(String.format(" %s  ", (char)('A' + col)));
    }
    result.append(String.format("%n"));
    for (int row = 0; row < this.rows; row++) {
      result.append(String.format("%d %s", row, (topFrontLine == row + 1) ? "v " : "  "));
      for (int col = 0; col < this.cols; col++) {
        Position position = new Position(row, col);
        result.append((this.isTileEmptyAt(position)) ? "   " : this.getTileAt(position).toString());
        if (col < this.cols - 1) { result.append("|"); }
      }
      if (row == bottomFrontLine) {
        result.append(" ^");
      }
      if (row < this.rows - 1) {
        result.append(String.format("%n"));
        result.append((topFrontLine == row + 1) ? "  --" : "    ");
        result.append(Stream.generate(() -> "-").limit(this.cols*4 - 1 + ((row + 1 == bottomFrontLine) ? 2 : 0)).collect(Collectors.joining("")));
        result.append(String.format("%n"));
      }
    }
    return result.toString();
  }
}
