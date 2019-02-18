import static org.junit.jupiter.api.Assertions.assertEquals;

import stormboundanalyzer.Faction;

import org.junit.jupiter.api.Test;

class FactionTests {

    @Test
    void faction_names() {
      assertEquals("Neutral", Faction.NEUTRAL.getName());
      assertEquals("Winter Pact", Faction.WINTER_PACT.getName());
      assertEquals("Ironclad Union", Faction.IRONCLAD_UNION.getName());
      assertEquals("Swarm of the East", Faction.SWARM_OF_THE_EAST.getName());
      assertEquals("Tribes of Shadowfen", Faction.TRIBES_OF_SHADOWFEN.getName());
    }

}
