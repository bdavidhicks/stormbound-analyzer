import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.stormboundanalyzer.PlayerBase;

import org.junit.jupiter.api.Test;

class PlayerBaseTests {

    @Test
    void player_base_strength_10_thru_20() {
      assertDoesNotThrow(() -> {
        PlayerBase b;
        for (int i = 10; i <= 20; i++) {
          b = new PlayerBase(i);
          assertEquals(i, b.getCurrentStrength());
        }
      });
    }

    @Test
    void player_base_strength_invalid() {
      assertThrows(Exception.class, () -> { PlayerBase bnegative = new PlayerBase(-14654); });
      assertThrows(Exception.class, () -> { PlayerBase b0 = new PlayerBase(9); });
      assertThrows(Exception.class, () -> { PlayerBase b21 = new PlayerBase(21); });
      assertThrows(Exception.class, () -> { PlayerBase blarge = new PlayerBase(465871); });
    }

}
