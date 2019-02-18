package stormboundanalyzer;

public class Base{
  int health;

  public int getHealth() {
    return this.health;
  }

  public Base(int playerLevel) throws Exception {
    if (playerLevel >= 1 && playerLevel <= 3) {
      this.health = 10;
    } else if (playerLevel >= 4 && playerLevel <= 6) {
      this.health = 11;
    } else if (playerLevel >= 7 && playerLevel <= 8) {
      this.health = 12;
    } else if (playerLevel >= 9 && playerLevel <= 10) {
      this.health = 13;
    } else if (playerLevel >= 11 && playerLevel <= 12) {
      this.health = 14;
    } else if (playerLevel >= 13 && playerLevel <= 14) {
      this.health = 15;
    } else if (playerLevel >= 15 && playerLevel <= 16) {
      this.health = 16;
    } else if (playerLevel == 17) {
      this.health = 17;
    } else if (playerLevel == 18) {
      this.health = 18;
    } else if (playerLevel == 19) {
      this.health = 19;
    } else if (playerLevel == 20) {
      this.health = 20;
    } else {
      System.out.format("Invalid player level of %d given", playerLevel);
      throw new Exception("Invalid player level");
    }
  }
}
