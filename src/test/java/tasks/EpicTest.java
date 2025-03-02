package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void testEpicCreation() {
        Epic epic = new Epic("Эпик1", "Описание эпика");
        // id должен быть 0, так как не назначен менеджером
        assertEquals(0, epic.getId());
        // Проверка имени и описания
        assertEquals("Эпик1", epic.getName());
        assertEquals("Описание эпика", epic.getDescription());
        // Статус должен быть NEW
        assertEquals(Status.NEW, epic.getStatus());
        // Список подзадач должен быть пустым
        assertTrue(epic.getSubtaskIds().isEmpty());
    }
}
