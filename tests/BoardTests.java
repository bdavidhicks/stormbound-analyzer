import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import stormboundanalyzer.Board;

import org.junit.jupiter.api.Test;

class BoardTests {

    @Test
    void board_create_valid() {
      assertDoesNotThrow(() -> {
        Board b1 = new Board(5,4);
        assertEquals(5, b1.getRows());
        assertEquals(4, b1.getCols());
      });
    }

    @Test
    void board_create_invalid() {
      assertThrows(Exception.class, () -> { Board brow0 = new Board(0, 1); });
      assertThrows(Exception.class, () -> { Board bcol0 = new Board(1, 0); });
    }

}
