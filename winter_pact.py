import random
from cards import Frostling, Unit
from factions import Factions


class Frosthexers(Frostling):
    def __init__(self, level):
        self.name = 'Frosthexers'
        if level == 1 or level == 2:
            self.text = '''On play, freeze a random
 bordering enemy unit'''
        elif level == 3 or level == 4:
            self.text = '''On play, freeze all
 bordering enemy units.'''
        elif level == 5:
            self.text = '''On play, freeze all
 surrounding enemy units'''
        self.level = level
        self.faction = Factions.WINTER_PACT
        self.cost = 1
        self.strength = level // 2 + 1
        self.movement = 0
        super(Frosthexers, self).__init__(
            self.name, self.text, self.level, self.faction, self.cost,
            self.strength, self.movement)

    def on_play(self, board, player, position):
        possible = board.get_bordering_list(position) if self.level >= 1 and \
            self.level <= 4 else board.get_surrounding(position)
        targets = [c for c in possible if not c.is_empty() and
                   player != c.owner and isinstance(c.card, Unit)]
        if len(targets) > 0:
            if self.level >= 1 and self.level <= 2:
                choice = random.choice(targets)
                choice.card.frozen = True
            elif self.level >= 3 and self.level <= 5:
                for target in targets:
                    target.card.frozen = True
