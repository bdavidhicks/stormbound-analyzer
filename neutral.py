from cards import Construct, Unit
from factions import Factions
import random


class GreenPrototypes(Construct):
    def __init__(self, level):
        self.name = 'Green Prototypes'
        self.text = '''On death, give 2 strength to a
 random bordering ENEMY unit'''
        self.level = level
        self.faction = Factions.NEUTRAL
        self.cost = 1
        self.strength = level + 1
        self.movement = 1
        super(GreenPrototypes, self).__init__(
            self.name, self.text, self.level, self.faction, self.cost,
            self.strength, self.movement)

    def on_death(self, board, player, position):
        # give a random boarding ENEMY unit 2 strength
        bordering = board.get_bordering_list(position)
        targets = [c for c in bordering if not c.is_empty() and
                   player != c.owner and isinstance(c.card, Unit) and
                   c.card.current_strength > 0]
        print(targets)
        if len(targets) > 0:
            choice = random.choice(targets)
            choice.card.current_strength += 2
