import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import stormboundanalyzer.Base;

import org.junit.jupiter.api.Test;

class BaseTests {

    @Test
    void base_player_level_1_thru_3() {
      assertDoesNotThrow(() -> { Base b1 = new Base(1); assertEquals(10, b1.getHealth()); });
      assertDoesNotThrow(() -> { Base b2 = new Base(2); assertEquals(10, b2.getHealth()); });
      assertDoesNotThrow(() -> { Base b3 = new Base(3); assertEquals(10, b3.getHealth()); });
    }

    @Test
    void base_player_level_4_thru_6() {
      assertDoesNotThrow(() -> { Base b4 = new Base(4); assertEquals(11, b4.getHealth()); });
      assertDoesNotThrow(() -> { Base b5 = new Base(5); assertEquals(11, b5.getHealth()); });
      assertDoesNotThrow(() -> { Base b6 = new Base(6); assertEquals(11, b6.getHealth()); });
    }

    @Test
    void base_player_level_7_thru_8() {
      assertDoesNotThrow(() -> { Base b7 = new Base(7); assertEquals(12, b7.getHealth()); });
      assertDoesNotThrow(() -> { Base b8 = new Base(8); assertEquals(12, b8.getHealth()); });
    }

    @Test
    void base_player_level_9_thru_10() {
      assertDoesNotThrow(() -> { Base b9 = new Base(9); assertEquals(13, b9.getHealth()); });
      assertDoesNotThrow(() -> { Base b10 = new Base(10); assertEquals(13, b10.getHealth()); });
    }

    @Test
    void base_player_level_11_thru_12() {
      assertDoesNotThrow(() -> { Base b11 = new Base(11); assertEquals(14, b11.getHealth()); });
      assertDoesNotThrow(() -> { Base b12 = new Base(12); assertEquals(14, b12.getHealth()); });
    }

    @Test
    void base_player_level_13_thru_14() {
      assertDoesNotThrow(() -> { Base b13 = new Base(13); assertEquals(15, b13.getHealth()); });
      assertDoesNotThrow(() -> { Base b14 = new Base(14); assertEquals(15, b14.getHealth()); });
    }

    @Test
    void base_player_level_15_thru_16() {
      assertDoesNotThrow(() -> { Base b15 = new Base(15); assertEquals(16, b15.getHealth()); });
      assertDoesNotThrow(() -> { Base b16 = new Base(16); assertEquals(16, b16.getHealth()); });
    }

    @Test
    void base_player_level_17_thru_20() {
      assertDoesNotThrow(() -> { Base b17 = new Base(17); assertEquals(17, b17.getHealth()); });
      assertDoesNotThrow(() -> { Base b18 = new Base(18); assertEquals(18, b18.getHealth()); });
      assertDoesNotThrow(() -> { Base b19 = new Base(19); assertEquals(19, b19.getHealth()); });
      assertDoesNotThrow(() -> { Base b20 = new Base(20); assertEquals(20, b20.getHealth()); });
    }

    @Test
    void base_player_level_invalid() {
      assertThrows(Exception.class, () -> { Base bnegative = new Base(-14654); });
      assertThrows(Exception.class, () -> { Base b0 = new Base(0); });
      assertThrows(Exception.class, () -> { Base b21 = new Base(21); });
      assertThrows(Exception.class, () -> { Base blarge = new Base(465871); });
    }

}
