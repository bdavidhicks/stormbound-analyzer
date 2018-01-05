from factions import Factions
from base import Base


class Player:
    def __init__(self, faction, is_opponent, level, goes_first, front_line):
        if isinstance(faction, Factions) and faction != Factions.NEUTRAL:
            self.current_mana = 3 if goes_first else 4
            self.is_opponent = is_opponent
            self.faction = faction
            self.level = level
            self.name = 'Opp' if is_opponent else 'You'
            self.base = Base(level)
            self.base_line = front_line
            self.front_line = self.base_line
        else:
            raise ValueError('Invalid Faction supplied to player constructor')

    def calc_front_line(self, board):
        rows = reversed(range(board.rows)) if self.is_opponent else \
            range(board.rows)
        for row in rows:
            for space in board.spaces[row]:
                if not space.is_empty() and space.owner == self:
                    if self.is_opponent:
                        return max(
                            self.base_line, min(board.rows - 1, row + 1))
                    else:
                        return min(self.base_line, max(1, row))
        return self.base_line

    def reset_front_line(self):
        self.front_line = self.base_line

    def is_behind_front_line(self, position):
        if self.is_opponent:
            return position.row <= self.front_line
        else:
            return position.row >= self.front_line

    def __repr__(self):
        return '{name} - {faction} with {base_health} health base with \
line at {front_line}'.format(
                    name=self.name,
                    faction=self.faction,
                    base_health=self.base.health,
                    front_line=self.front_line
                )

    def __str__(self):
        return self.__repr__()
