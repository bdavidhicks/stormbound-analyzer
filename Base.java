package com.stormboundanalyzer;

public class Base{
  int health;

  public Base(int health) throws Exception {
    if (health >= 10 && health <= 20) {
      this.health = health;
    } else {
      System.out.format("Invalid Base health of %d given", health);
      throw new Exception("Invalid Base health");
    }
  }
  public int getHealth() {return this.health;}
  public void takeDamage(int damage) {this.health -= damage;}
}
