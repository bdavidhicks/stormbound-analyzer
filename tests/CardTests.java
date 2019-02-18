import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import stormboundanalyzer.Faction;
import stormboundanalyzer.Card;

import org.junit.jupiter.api.Test;

class CardTests {

    @Test
    void card_valid() {
      assertDoesNotThrow(() -> { Card c1 = new Card("Test card", "This is a test card", 1, Faction.NEUTRAL, 0); } );
      assertDoesNotThrow(() -> { Card c2 = new Card("Test card 2", "This is a test card", 2, Faction.WINTER_PACT, 1); } );
      assertDoesNotThrow(() -> { Card c3 = new Card("Test card 3", "This is a test card", 3, Faction.TRIBES_OF_SHADOWFEN, 2); } );
      assertDoesNotThrow(() -> { Card c4 = new Card("Test card 4", "This is a test card", 4, Faction.SWARM_OF_THE_EAST, 3); } );
      assertDoesNotThrow(() -> { Card c5 = new Card("Test card 5", "This is a test card", 5, Faction.IRONCLAD_UNION, 4); } );
    }

    @Test
    void card_invalid_levels() {
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", -2546, Faction.NEUTRAL, 0); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", -2, Faction.NEUTRAL, 0); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 0, Faction.NEUTRAL, 0); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 6, Faction.NEUTRAL, 0); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 12, Faction.NEUTRAL, 0); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 12345, Faction.NEUTRAL, 0); } );
    }

    @Test
    void card_invalid_costs() {
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 1, Faction.NEUTRAL, -6542); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 1, Faction.NEUTRAL, -62); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 1, Faction.NEUTRAL, -32); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 1, Faction.NEUTRAL, -2); } );
      assertThrows(Exception.class, () -> { Card c1 = new Card("Test card", "This is a test card", 1, Faction.NEUTRAL, -1); } );
    }

}
