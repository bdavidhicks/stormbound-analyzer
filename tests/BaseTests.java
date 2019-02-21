import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.stormboundanalyzer.Base;

import org.junit.jupiter.api.Test;

class BaseTests {

    @Test
    void base_player_level_10_thru_20() {
      assertDoesNotThrow(() -> {
        Base b;
        for (int i = 10; i <= 20; i++) {
          b = new Base(i);
          assertEquals(i, b.getHealth());
        }
      });
    }

    @Test
    void base_player_level_invalid() {
      assertThrows(Exception.class, () -> { Base bnegative = new Base(-14654); });
      assertThrows(Exception.class, () -> { Base b0 = new Base(9); });
      assertThrows(Exception.class, () -> { Base b21 = new Base(21); });
      assertThrows(Exception.class, () -> { Base blarge = new Base(465871); });
    }

}
