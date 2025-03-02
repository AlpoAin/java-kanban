package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void testSubtaskCreation() {
        // Создаем подзадачу для эпика с id=1 (до добавления в менеджер)
        Subtask subtask = new Subtask("Подзадача1", "Описание подзадачи", 1);
        // id должен быть 0 до назначения менеджером
        assertEquals(0, subtask.getId());
        // Проверка имени и описания
        assertEquals("Подзадача1", subtask.getName());
        assertEquals("Описание подзадачи", subtask.getDescription());
        // Статус должен быть NEW
        assertEquals(Status.NEW, subtask.getStatus());
        // epicId должен соответствовать переданному значению
        assertEquals(1, subtask.getEpicId());
    }
}
