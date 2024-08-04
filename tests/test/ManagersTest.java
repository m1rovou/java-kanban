package tests.test;

import managers.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagerTest {

    @Test
    void shouldNotBeNullTaskManager() {
        assertNotNull(Managers.getDefault());
    }

    @Test
    void shouldNotBeNullHistoryManager() {
        assertNotNull(Managers.getDefaultHistory());
    }
}