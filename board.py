from colorama import Fore, Back, Style, init
from cards import Summon
init()


class Position:
    def __init__(self, row, column):
        self.row = row
        self.col = column

    def add_to_row(self, add_amount):
        return Position(self.row + add_amount, self.col)

    def add_to_col(self, add_amount):
        return Position(self.row, self.col + add_amount)

    def __repr__(self):
        return '({r},{c})'.format(r=self.row, c=self.col)

    def __str__(self):
        return self.__repr__()


class Space:
    def __init__(self, position):
        if isinstance(position, Position):
            self.position = position
            self.card = None
            self.owner = None

    def is_empty(self):
        if self.card is not None and self.owner is not None:
            if isinstance(self.card, Summon):
                return False
            else:
                raise ValueError(
                    'Card on the board should be a sub class of Summon')
        else:
            return True

    def __repr__(self):
        if self.card is not None and self.owner is not None:
            display = str(self.card).rjust(3, ' ') + ' '
            if self.owner.is_opponent:
                return '{}{}{}'.format(Back.RED, display, Style.RESET_ALL)
            else:
                return '{}{}{}'.format(Back.GREEN, display, Style.RESET_ALL)
        else:
            # return str(self.position)
            return ' '*4

    def __str__(self):
        return self.__repr__()


class Board:
    """
    Double array of Positions 'rows' tall and 'cols' wide
    Example of the repr with 5 rows and 4 cols:
        |    |    |
    --------------------
        |    |    |
    --------------------
        |    |    |
    --------------------
        |    |    |
    --------------------
        |    |    |
    """
    def __init__(self, rows, cols):
        if rows > 0 and cols > 0:
            self.rows = rows
            self.cols = cols
            self.spaces = [[Space(Position(row, col))
                            for col in range(cols)]
                           for row in range(rows)]
        else:
            raise ValueError('Rows and cols must be greater than 0')

    def is_empty(self, position):
        return self.spaces[position.row][position.col].is_empty()

    def get_bordering_list(self, position):
        result = []
        if position.row + 1 < self.rows:
            result.append(self.spaces[position.row + 1][position.col])
        if position.col + 1 < self.cols:
            result.append(self.spaces[position.row][position.col + 1])
        if position.row - 1 >= 0:
            result.append(self.spaces[position.row - 1][position.col])
        if position.col - 1 >= 0:
            result.append(self.spaces[position.row][position.col - 1])
        return result

    def get_bordering_nesw(self, position):
        result = {
            'north': None,
            'east': None,
            'south': None,
            'west': None
        }
        result['north'] = self.spaces[position.row - 1][position.col] \
            if position.row - 1 < self.rows else None
        result['east'] = self.spaces[position.row][position.col + 1] \
            if position.col + 1 < self.cols else None
        result['south'] = self.spaces[position.row + 1][position.col] \
            if position.row + 1 < self.rows else None
        result['west'] = self.spaces[position.row][position.col - 1] \
            if position.col - 1 >= 0 else None
        return result

    def get_surrounding(self, position):
        result = []
        for row_adj in range(-1, 2):
            for col_adj in range(-1, 2):
                if row_adj != 0 or col_adj != 0:
                    new_row = position.row + row_adj
                    new_col = position.col + col_adj
                    if new_row >= 0 and new_row < self.rows and \
                            new_col >= 0 and new_col < self.cols:
                        result.append(self.spaces[new_row][new_col])
        return result

    def summon(self, card, player, position):
        space = self.spaces[position.row][position.col]
        if self.is_empty(position) and player.is_behind_front_line(position):
            space.card = card
            space.owner = player
        else:
            print('Attempted to summon on board {card} at {pos} for {player} \
                   but failed'.format(
                    card=card.name,
                    pos=position,
                    player=player.name
                ))

    def to_string(self, front_line, opp_front_line):
        result = ''
        for row in range(self.rows):
            result += '|'.join([str(space) for space in self.spaces[row]])
            if not row == self.rows - 1:
                if opp_front_line - 1 == row and front_line - 1 == row:
                    color = Fore.BLACK
                elif opp_front_line - 1 == row:
                    color = Fore.RED
                elif front_line - 1 == row:
                    color = Fore.GREEN
                else:
                    color = Style.RESET_ALL
                result += '\n{}{}{}\n'.format(
                    color,
                    '-'*(self.cols*5),
                    Style.RESET_ALL
                )
        return result
