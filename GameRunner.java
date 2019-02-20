package stormboundanalyzer;

import java.util.Scanner;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameRunner {

  public final Scanner sc = new Scanner(System.in);

  public static void main(String[] args) throws Exception {
    GameRunner gr = new GameRunner();

    List<Faction> factions = Arrays.stream(Faction.values())
      .filter(f -> !f.equals(Faction.NEUTRAL))
      .collect(Collectors.toList());
    Faction bottomPlayerFaction = gr.chooseFromList(gr.sc, "Pick your Faction using a number: ", factions);
    int bottomPlayerLevel = gr.chooseFromRange(gr.sc, "Pick your base health: ", 10, 20);
    Faction topPlayerFaction = gr.chooseFromList(gr.sc, "Pick your opponent's Faction using a number: ", factions);
    int topPlayerLevel = gr.chooseFromRange(gr.sc, "Pick your opponent's base health: ", 10, 20);
    boolean bottomPlayerGoesFirst = gr.answerYesNo(gr.sc, "Do you go first?: ");
    Game game = new Game(
      5, // Board height
      4, // Board width
      bottomPlayerFaction,
      bottomPlayerLevel,
      topPlayerFaction,
      topPlayerLevel,
      bottomPlayerGoesFirst
    );
    System.out.println(game.toString());
    List<Card> cards = new ArrayList<Card>();
    for (int level = 1; level <= 5; level++) {
      cards.add(new GreenPrototypes(level));
      cards.add(new LawlessHerd(level));
    }
    Collections.sort(cards);

    while (!game.isOver()) {
      char action = gr.chooseAction(gr.sc);
      if (action == 'p') {
        Card cardToPlay = gr.chooseFromList(gr.sc, "Pick a card to play: ", cards);
        Position locationToPlay = gr.chooseLocation(gr.sc, "Pick a location to play: a1 a2 ... d4 ");
        gr.playCard(game, game.getActivePlayer(), locationToPlay, cardToPlay.copyCard());
      } else if (action == 'd') {
        Card cardToDiscard = gr.chooseFromList(gr.sc, "Pick a card to discard: ", cards);
      } else if (action == 'e') {
        gr.nextTurn(game);
      }
    }
    System.out.println("---------GAME OVER---------");
    System.out.println(String.format("%s won the game", game.getWinner()));
    System.exit(0);
  }
  public void nextTurn(Game game) {
    game.endTurn();
    System.out.println(String.format("Start Turn %d: %s is the Active Player", game.getTurnCounter(), game.getActivePlayer().getName()));
    game.startTurn();
    System.out.println(game.toString());
  }

  public void playCard(Game game, Player player, Position position, Card card) {
    if (card.canPlay(game, player, position)) {
      System.out.println(String.format("%s playing level %d %s at %s", player.getName(), card.getLevel(), card.getName(), position.toString()));
      card.play(game, player, position);
      System.out.println(game.toString());
    } else {
      System.out.println(String.format("%s unable to play level %d %s at %s", player.getName(), card.getLevel(), card.getName(), position.toString()));
    }
  }

  public <T> T chooseFromList(Scanner sc, String prompt, List<T> list) {
    int choice;
    do {
      System.out.println(prompt);
      for (int index = 0; index < list.size(); index++) {
        System.out.println(String.format("%2d: %s", index + 1, list.get(index).toString()));
      }
      while (!sc.hasNextInt()) {
        System.out.println("That's not a number!");
        sc.next();
      }
      choice = sc.nextInt();
    } while (choice <= 0 || choice > list.size());
    return list.get(choice - 1);
  }

  public int chooseFromRange(Scanner sc, String prompt, int low, int high) {
    int choice;
    do {
      System.out.println(prompt);
      System.out.println(String.format("Valid values are from %d to %d", low, high));
      while (!sc.hasNextInt()) {
        System.out.println("That's not a number!");
        sc.next();
      }
      choice = sc.nextInt();
    } while (choice < low || choice > high);
    return choice;
  }


  public boolean answerYesNo(Scanner sc, String prompt) {
    Pattern yesNo = Pattern.compile("[yY](es)?|[nN](o)?");
    String choice;
    System.out.println(prompt);
    System.out.println("(y)es or (n)o");
    while (!sc.hasNext(yesNo)) {
      System.out.println("That's not a yes or no!");
      sc.next();
    }
    choice = sc.next();
    return choice.equals("yes") || choice.equals("Yes") || choice.equals("y") || choice.equals("Y");
  }

  public Position chooseLocation(Scanner sc, String prompt) {
    Pattern location = Pattern.compile("[abcd][01234]");
    String choice;
    System.out.println(prompt);
    while (!sc.hasNext(location)) {
      System.out.println("That's not a valid location!");
      sc.next();
    }
    choice = sc.next();
    int col = choice.charAt(0) - 'a';
    int row = Integer.parseInt("" + choice.charAt(1));
    return new Position(row, col);
  }

  public char chooseAction(Scanner sc) {
    Pattern action = Pattern.compile("[pP](lay)?|[eE](nd)?|[dD](iscard)?");
    String choice;
    System.out.println("Pick an action: (p)lay or (e)nd or (d)iscard");
    while (!sc.hasNext(action)) {
      System.out.println("That's not a valid action!");
      sc.next();
    }
    choice = sc.next().toLowerCase();
    return choice.charAt(0);
  }

}
