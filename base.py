class Base:
    def __init__(self, player_level):
        if player_level >= 1 and player_level <= 3:
            self.health = 10
        elif player_level >= 4 and player_level <= 6:
            self.health = 11
        elif player_level >= 7 and player_level <= 8:
            self.health = 12
        elif player_level >= 9 and player_level <= 10:
            self.health = 13
        elif player_level >= 11 and player_level <= 12:
            self.health = 14
        elif player_level >= 13 and player_level <= 14:
            self.health = 15
        elif player_level >= 15 and player_level <= 16:
            self.health = 16
        elif player_level == 17:
            self.health = 17
        elif player_level == 18:
            self.health = 18
        elif player_level == 19:
            self.health = 19
        elif player_level == 19:
            self.health = 20
        else:
            print('Invalid player level of {level} given'.format(
                level=player_level))
            raise
