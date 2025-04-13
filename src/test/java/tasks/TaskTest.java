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
    void testSetStatus() {
        Task task = new Task("Zadacha", "Opisanie");
        task.setStatus(Status.DONE);
        assertEquals(Status.DONE, task.getStatus());
    }
}
