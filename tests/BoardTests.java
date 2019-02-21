import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.stormboundanalyzer.Board;
import com.stormboundanalyzer.Game;
import com.stormboundanalyzer.Faction;

import org.junit.jupiter.api.Test;

class BoardTests {

    @Test
    void board_create_valid() {
      assertDoesNotThrow(() -> {
        Game g = new Game(
          5,
          4,
          Faction.WINTER_PACT,
          10,
          Faction.WINTER_PACT,
          20,
          true
        );
        assertEquals(5, g.getBoard().getRows());
        assertEquals(4, g.getBoard().getCols());
      });
    }

    @Test
    void board_create_invalid() {
      assertThrows(Exception.class, () -> {
        Game g = new Game(
          0,
          1,
          Faction.WINTER_PACT,
          10,
          Faction.WINTER_PACT,
          20,
          true
        );
      });
      assertThrows(Exception.class, () -> {
        Game g = new Game(
          1,
          0,
          Faction.WINTER_PACT,
          10,
          Faction.WINTER_PACT,
          20,
          true
        );
      });
    }

}
