package com.stormboundanalyzer;

import java.util.Comparator;

class Position implements Comparable<Position>{
  int row, col;

  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public boolean equals(Object b) {
    if (b instanceof Position) {
      Position other = (Position)b;
      return this.row == other.row && this.col == other.col;
    }
    return false;
  }
  public int getRow() {return this.row;}
  public int getCol() {return this.col;}
  public int moveRow(int amount) {this.row += amount; return this.row;}
  public int moveCol(int amount) {this.col += amount; return this.col;}

  @Override
  public int compareTo(Position other) {
    return (this.getRow() - other.getRow() == 0) ? this.getCol() - other.getCol() : this.getRow() - other.getRow();
  }

  public String toString() {
    return String.format("(%d,%d)", this.row, this.col);
  }

  public String toHumanString() {
    return String.format("%s%s", (char)('a' + this.col), this.row);
  }
}
