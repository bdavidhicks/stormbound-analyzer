package com.stormboundanalyzer;

class Position {
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

  public String toString() {
    return String.format("(%d,%d)", this.row, this.col);
  }
}
