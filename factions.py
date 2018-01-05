from enum import Enum


class NoValue(Enum):
    def __repr__(self):
        return self.value

    def __str__(self):
        return self.__repr__()


class Factions(NoValue):
    IRONCLAD_UNION = 'Ironclad Union'
    NEUTRAL = 'Neutral'
    SWARM_OF_THE_EAST = 'Swarm of the East'
    TRIBES_OF_SHADOWFEN = 'Tribes of Shadowfen'
    WINTER_PACT = 'Winter Pact'
