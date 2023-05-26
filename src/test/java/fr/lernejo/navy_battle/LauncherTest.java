package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class LauncherTest {
    @Test
    void mainTest() {
        assertDoesNotThrow(() -> Launcher.main(new String[]{"1234"}));
    }
}