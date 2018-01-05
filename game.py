from board import Board, Position
from factions import Factions
from player import Player
from neutral import GreenPrototypes
from winter_pact import Frosthexers


class Game:
    def __init__(self, board, bottom_player, top_player,
                 bottom_player_goes_first):
        if isinstance(board, Board) and len(bottom_player) == 2 and \
                len(top_player) == 2:
            self.board = board
            self.bottom_player = Player(
                bottom_player[0],
                False,
                bottom_player[1],
                bottom_player_goes_first,
                self.board.rows - 1
            )
            self.top_player = Player(
                top_player[0],
                True,
                top_player[1],
                not bottom_player_goes_first,
                1)
        else:
            raise ValueError('Invalid board or players for game constructor')

    def __repr__(self):
        return '\n{top}\n\n{board}\n\n{bottom}\n'.format(
            top=self.top_player,
            board=self.board.to_string(
                self.bottom_player.front_line,
                self.top_player.front_line),
            bottom=self.bottom_player
        )

    def __str__(self):
        return self.__repr__()


if __name__ == '__main__':
    game = Game(
        Board(5, 4),
        (Factions.WINTER_PACT, 6),
        (Factions.TRIBES_OF_SHADOWFEN, 7),
        True
    )
    gp = GreenPrototypes(1)
    gp_1 = GreenPrototypes(2)
    fh = Frosthexers(1)
    gp_2 = GreenPrototypes(1)
    gp_3 = GreenPrototypes(1)
    gp_4 = GreenPrototypes(1)
    gp_5 = GreenPrototypes(1)

    pos_1 = Position(4, 3)
    pos_2 = Position(0, 1)
    pos_3 = Position(3, 0)
    pos_4 = Position(2, 2)
    pos_5 = Position(1, 1)
    pos_6 = Position(0, 2)
    if gp.can_play(game.board, game.bottom_player, pos_1):
        print('bottom player playing gp')
        gp.play(game.board, game.bottom_player, pos_1)
    if fh.can_play(game.board, game.top_player, pos_2):
        print('top player playing fh')
        fh.play(game.board, game.top_player, pos_2)
    print(game)
    if gp_2.can_play(game.board, game.bottom_player, pos_3):
        print('bottom player playing gp_2')
        gp_2.play(game.board, game.bottom_player, pos_3)
    print(game)
    if gp_3.can_play(game.board, game.bottom_player, pos_4):
        print('bottom player playing gp_3')
        gp_3.play(game.board, game.bottom_player, pos_4)
    print(game)
    game.bottom_player.current_mana = 5
    if gp_4.can_play(game.board, game.bottom_player, pos_5):
        print('bottom player playing gp_4')
        gp_4.play(game.board, game.bottom_player, pos_5)
    print(game)
    if gp_5.can_play(game.board, game.top_player, pos_6):
        print('top player playing gp_5')
        gp_5.play(game.board, game.top_player, pos_6)
    print(game)
