package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void testEpicCreation() {
        Epic epic = new Epic("Эпик1", "Описание эпика");
        assertEquals(0, epic.getId());
        assertEquals("Эпик1", epic.getName());
        assertEquals("Описание эпика", epic.getDescription());
        assertEquals(Status.NEW, epic.getStatus());
        assertTrue(epic.getSubtaskIds().isEmpty());
    }

    @Test
    void testAddSubtaskId() {
        Epic epic = new Epic("Epic", "Epic desc");
        epic.addSubtask(10);
        epic.addSubtask(20);
        assertTrue(epic.getSubtaskIds().contains(10));
        assertTrue(epic.getSubtaskIds().contains(20));
    }
}
