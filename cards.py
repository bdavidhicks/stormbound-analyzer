from factions import Factions


class Card:
    def __init__(self, name, text, level, faction, cost):
        if isinstance(faction, Factions):
            self.name = name
            self.text = text
            if level < 1 or level > 5:
                raise ValueError('Card level must be between 1 and 5')
            self.level = level
            self.faction = faction
            self.cost = cost
        else:
            raise ValueError('Invalid Faction supplied to card constructor')

    def can_play(self, board, player, position):
        if player.current_mana < self.cost:
            return False
        else:
            return True

    def play(self, board, player, position):
        if player.current_mana >= self.cost:
            player.current_mana -= self.cost
            # TODO: remove card from hand
        else:
            print('''Attempted to play {card} which costs {cost} but {player}
                only has {current_mana} mana'''.format(
                    card=self.name,
                    cost=self.cost,
                    player=player.name,
                    current_mana=player.current_mana
            ))


class Spell(Card):
    def __init__(self, name, text, level, faction, cost, has_target,
                 valid_targets):
        super(Summon, self).__init__(name, text, level, faction, cost)
        self.has_target = has_target
        self.valid_targets = valid_targets


class Summon(Card):
    def __init__(self, name, text, level, faction, cost, strength):
        super(Summon, self).__init__(name, text, level, faction, cost)
        self.strength = strength
        self.current_strength = self.strength
        self.frozen = False
        self.poisoned = False

    def can_play(self, board, player, position):
        if super(Summon, self).can_play(board, player, position) and \
                board.is_empty(position) and \
                player.is_behind_front_line(position):
            return True
        else:
            return False

    def play(self, board, player, position):
        if self.can_play(board, player, position):
            super(Summon, self).play(board, player, position)
            board.summon(self, player, position)
        else:
            print('''Attempted to summon {card} at {pos} for {player}
                but failed'''.format(
                    card=self.name,
                    pos=position,
                    player=player.name
            ))

    def is_frozen(self):
        return self.frozen

    def is_alive(self):
        return self.current_strength > 0

    def is_poisoned(self):
        return self.poisoned

    def on_turn_begin(self, board, player, position):
        pass

    def on_death(self, board, player, position):
        pass

    def __repr__(self):
        return str(self.current_strength)

    def __str__(self):
        return self.__repr__()


class Unit(Summon):
    def __init__(self, name, text, level, faction, cost, strength, movement):
        super(Unit, self).__init__(name, text, level, faction, cost, strength)
        self.movement = movement

    def can_play(self, board, player, position):
        return super(Unit, self).can_play(board, player, position)

    def play(self, board, player, position):
        if self.can_play(board, player, position):
            super(Unit, self).play(board, player, position)
            curr_pos = position
            remaining_movement = self.movement
            while remaining_movement > 0 and self.current_strength > 0:
                next_attack = self.calc_next_attack(board, player, curr_pos)
                if next_attack is not None:
                    self.on_attack(board, player, curr_pos, next_attack)
                    self.attack(board, player, curr_pos, next_attack)
                    curr_pos = next_attack
                else:
                    nesw = board.get_bordering_nesw(position)
                    new_pos = nesw['south'].position if player.is_opponent \
                        else nesw['north'].position
                    self.move(board, player, position, new_pos)
                remaining_movement -= 1
        else:
            print('Attempted to summon unit {card} at {pos} for {player} \
but failed'.format(
                    card=self.name,
                    pos=position,
                    player=player.name
            ))

    def move(self, board, player, position, new_position):
        new_space = board.spaces[new_position.row][new_position.col]
        if new_space is not None and not new_space.is_empty():
            print('{c} attempted to move into an occupied space {pos}'.format(
                c=self.name,
                pos=new_space.position
            ))
        else:
            new_space.card = self
            new_space.owner = player
            board.spaces[position.row][position.col].card = None
            board.spaces[position.row][position.col].owner = None
            player.front_line = player.calc_front_line(board)

    def calc_next_attack(self, board, player, position):
        bd = board.get_bordering_nesw(position)
        front = bd['south'] if player.is_opponent else bd['north']
        if front is not None and not front.is_empty() and \
                front.owner != player:
            return front.position
        east = bd['east'] if bd['east'] is not None and not bd['east'].is_empty() and \
            bd['east'].owner != player else None
        west = bd['west'] if bd['west'] is not None and not bd['west'].is_empty() and \
            bd['west'].owner != player else None
        if east is not None and west is not None:
            if abs((board.cols - 1) / 2 - east.position.col) < \
                    abs((board.cols - 1) / 2 - west.position.col):
                return east.position
            return west.position
        elif east is not None:
            return east.position
        return west.position if west is not None else None

    def on_attack(self, board, player, attack_pos, defend_pos):
        pass

    def attack(self, board, player, attack_pos, defend_pos):
        att_sp = board.spaces[attack_pos.row][attack_pos.col]
        def_sp = board.spaces[defend_pos.row][defend_pos.col]
        remove_strength = min(
            att_sp.card.current_strength, def_sp.card.current_strength)
        print(remove_strength)
        att_player = att_sp.owner
        def_player = def_sp.owner
        print('att_sp.card.current_strength before = {}'.format(att_sp.card.current_strength))
        print('def_sp.card.current_strength before = {}'.format(def_sp.card.current_strength))
        att_sp.card.current_strength -= remove_strength
        def_sp.card.current_strength -= remove_strength
        print('att_sp.card.current_strength after = {}'.format(att_sp.card.current_strength))
        print('def_sp.card.current_strength after = {}'.format(def_sp.card.current_strength))
        if not def_sp.card.is_alive():
            print('defending card died')
            # TODO: this might have to change if the order of effects is wrong
            def_sp.card.on_death(board, def_player, defend_pos)
            def_sp.card = None
            def_sp.owner = None
            print('front line before = {}'.format(def_player.front_line))
            def_player.front_line = def_player.calc_front_line(board)
            print('front line after = {}'.format(def_player.front_line))
        if not att_sp.card.is_alive():
            print('attacking card died')
            # TODO: this might have to change if the order of effects is wrong
            att_sp.card.on_death(board, att_player, attack_pos)
            att_sp.card = None
            att_sp.owner = None
        else:
            print('att_sp.card = {}'.format(att_sp.card))
            att_sp.card.move(board, att_player, attack_pos, defend_pos)


class Structure(Summon):
    def __init__(self, name, text, level, faction, cost, strength):
        super(Structure, self).__init__(name, text, level, faction, cost,
                                        strength)


class Construct(Unit):
    def __init__(self, name, text, level, faction, cost, strength, movement):
        super(Construct, self).__init__(
            name, text, level, faction, cost, strength, movement)


class Frostling(Unit):
    def __init__(self, name, text, level, faction, cost, strength, movement):
        super(Frostling, self).__init__(
            name, text, level, faction, cost, strength, movement)
