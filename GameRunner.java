package stormboundanalyzer;

public class GameRunner {
  public static void main(String[] args) throws Exception {
    Game game = new Game(
      new Board(5, 4),
      Faction.WINTER_PACT,
      6,
      Faction.TRIBES_OF_SHADOWFEN,
      7,
      true
    );
    System.out.println(game.toString());
    // gp = GreenPrototypes(1)
    // gp_1 = GreenPrototypes(2)
    // fh = Frosthexers(1)
    // gp_2 = GreenPrototypes(1)
    // gp_3 = GreenPrototypes(1)
    // gp_4 = GreenPrototypes(1)
    // gp_5 = GreenPrototypes(1)
    //
    // pos_1 = Position(4, 3)
    // pos_2 = Position(0, 1)
    // pos_3 = Position(3, 0)
    // pos_4 = Position(2, 2)
    // pos_5 = Position(1, 1)
    // pos_6 = Position(0, 2)
    // if gp.can_play(game.board, game.bottom_player, pos_1):
    //     print('bottom player playing gp')
    //     gp.play(game.board, game.bottom_player, pos_1)
    // if fh.can_play(game.board, game.top_player, pos_2):
    //     print('top player playing fh')
    //     fh.play(game.board, game.top_player, pos_2)
    // print(game)
    // if gp_2.can_play(game.board, game.bottom_player, pos_3):
    //     print('bottom player playing gp_2')
    //     gp_2.play(game.board, game.bottom_player, pos_3)
    // print(game)
    // if gp_3.can_play(game.board, game.bottom_player, pos_4):
    //     print('bottom player playing gp_3')
    //     gp_3.play(game.board, game.bottom_player, pos_4)
    // print(game)
    // game.bottom_player.current_mana = 5
    // if gp_4.can_play(game.board, game.bottom_player, pos_5):
    //     print('bottom player playing gp_4')
    //     gp_4.play(game.board, game.bottom_player, pos_5)
    // print(game)
    // if gp_5.can_play(game.board, game.top_player, pos_6):
    //     print('top player playing gp_5')
    //     gp_5.play(game.board, game.top_player, pos_6)
    // print(game)
  }
}
