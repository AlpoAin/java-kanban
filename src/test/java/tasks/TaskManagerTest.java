package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class TaskManagerTest {

    @Test
    void testAddAndGetTasksById() {
        TaskManager manager = Managers.getDefault();

        Task task = new Task("Задача1", "Описание1");
        manager.addTask(task);
        int taskId = task.getId();
        Task fetchedTask = manager.getTask(taskId);
        assertNotNull(fetchedTask);
        assertEquals(task, fetchedTask);

        Epic epic = new Epic("Эпик1", "Описание эпика");
        manager.addEpic(epic);
        int epicId = epic.getId();
        Epic fetchedEpic = manager.getEpic(epicId);
        assertNotNull(fetchedEpic);
        assertEquals(epic, fetchedEpic);
        assertTrue(fetchedEpic.getSubtaskIds().isEmpty());

        Subtask subtask = new Subtask("Подзадача1", "Описание подзадачи", epicId);
        manager.addSubtask(subtask);
        int subtaskId = subtask.getId();
        Subtask fetchedSubtask = manager.getSubtask(subtaskId);
        assertNotNull(fetchedSubtask);
        assertEquals(subtask, fetchedSubtask);
        assertEquals(epicId, fetchedSubtask.getEpicId());
        Epic updatedEpic = manager.getEpic(epicId);
        assertTrue(updatedEpic.getSubtaskIds().contains(subtaskId));
    }

    @Test
    void testGetNonexistentTaskById() {
        TaskManager manager = Managers.getDefault();
        assertNull(manager.getTask(999));
        assertNull(manager.getEpic(999));
        assertNull(manager.getSubtask(999));
    }

    @Test
    void testHistoryRecording() {
        TaskManager manager = Managers.getDefault();
        Epic epic = new Epic("ЭпикИст", "Эпик для истории");
        manager.addEpic(epic);
        int epicId = epic.getId();

        Task t1  = new Task("T1",  "Desc1");  manager.addTask(t1);
        Task t2  = new Task("T2",  "Desc2");  manager.addTask(t2);
        Task t3  = new Task("T3",  "Desc3");  manager.addTask(t3);
        Task t4  = new Task("T4",  "Desc4");  manager.addTask(t4);
        Task t5  = new Task("T5",  "Desc5");  manager.addTask(t5);
        Task t6  = new Task("T6",  "Desc6");  manager.addTask(t6);
        Task t7  = new Task("T7",  "Desc7");  manager.addTask(t7);
        Task t8  = new Task("T8",  "Desc8");  manager.addTask(t8);
        Task t9  = new Task("T9",  "Desc9");  manager.addTask(t9);
        Task t10 = new Task("T10", "Desc10"); manager.addTask(t10);
        Task t11 = new Task("T11", "Desc11"); manager.addTask(t11);
        Subtask subH = new Subtask("ПодзадачаИст", "Подзадача для истории", epicId);
        manager.addSubtask(subH);
        int subHId = subH.getId();

        manager.getTask(t1.getId());
        manager.getEpic(epicId);
        manager.getSubtask(subHId);
        manager.getTask(t2.getId());
        manager.getTask(t3.getId());
        manager.getTask(t4.getId());
        manager.getTask(t5.getId());
        manager.getTask(t6.getId());
        manager.getTask(t7.getId());
        manager.getTask(t8.getId());
        manager.getTask(t9.getId());
        manager.getTask(t10.getId());
        manager.getTask(t11.getId());

        List<Task> history = manager.getHistory();
        assertTrue(history.size() <= 10);
        boolean containsT1 = history.stream().anyMatch(task -> task.getId() == t1.getId());
        assertFalse(containsT1);
        Task lastHistoryEntry = history.get(history.size() - 1);
        assertEquals(t11.getId(), lastHistoryEntry.getId());
        Task firstHistoryEntry = history.get(0);
        assertEquals(t2.getId(), firstHistoryEntry.getId());
    }

    @Test
    void testTaskDataImmutability() {
        TaskManager manager = Managers.getDefault();
        Task task = new Task("НезменяемаяЗадача", "Проверка неизменности");
        manager.addTask(task);
        int id = task.getId();
        Task retrieved1 = manager.getTask(id);
        retrieved1.setStatus(Status.DONE);
        Task retrieved2 = manager.getTask(id);
        assertEquals(Status.NEW, retrieved2.getStatus());
        assertNotSame(retrieved1, retrieved2);
    }

    @Test
    void testManagersGetDefault() {
        TaskManager manager1 = Managers.getDefault();
        TaskManager manager2 = Managers.getDefault();
        assertNotNull(manager1);
        assertTrue(manager1 instanceof TaskManager);
        assertTrue(manager1 instanceof InMemoryTaskManager);
        assertNotSame(manager1, manager2);
    }
}
