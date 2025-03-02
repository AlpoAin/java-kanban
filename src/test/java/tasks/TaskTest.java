package tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskCreation() {
        Task task = new Task("Задача1", "Описание задачи");
        assertEquals(0, task.getId());
        assertEquals("Задача1", task.getName());
        assertEquals("Описание задачи", task.getDescription());
        assertEquals(Status.NEW, task.getStatus());
    }

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
    void testSubtaskCreation() {
        Subtask subtask = new Subtask("Подзадача1", "Описание подзадачи", 1);
        assertEquals(0, subtask.getId());
        assertEquals("Подзадача1", subtask.getName());
        assertEquals("Описание подзадачи", subtask.getDescription());
        assertEquals(Status.NEW, subtask.getStatus());
        assertEquals(1, subtask.getEpicId());
    }
}
