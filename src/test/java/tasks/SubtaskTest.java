package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void testSubtaskCreation() {
        Subtask subtask = new Subtask("Подзадача1", "Описание подзадачи", 1);
        assertEquals(0, subtask.getId());
        assertEquals("Подзадача1", subtask.getName());
        assertEquals("Описание подзадачи", subtask.getDescription());
        assertEquals(Status.NEW, subtask.getStatus());
        assertEquals(1, subtask.getEpicId());
    }

    @Test
    void testSetStatus() {
        Subtask subtask = new Subtask("Sub", "desc", 99);
        subtask.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, subtask.getStatus());
    }
}
