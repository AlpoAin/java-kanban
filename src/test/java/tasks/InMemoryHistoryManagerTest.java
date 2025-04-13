package tasks;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void shouldReturnEmptyHistoryWhenNoTasksAdded() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой, если ничего не добавляли");
    }

    @Test
    void shouldAddTaskAndReturnInHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Task 1", "Description 1");

        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size(), "После добавления одной задачи размер истории должен быть 1");
        assertEquals(task1, history.get(0), "В истории должна быть именно task1");
    }

    @Test
    void shouldHandleMultipleTasksInOrder() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task t1 = new Task("Task 1", "Desc 1");
        Task t2 = new Task("Task 2", "Desc 2");
        Task t3 = new Task("Task 3", "Desc 3");

        historyManager.add(t1);
        historyManager.add(t2);
        historyManager.add(t3);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size(), "После добавления трёх задач должно быть 3 в истории");
        assertEquals(t1, history.get(0));
        assertEquals(t2, history.get(1));
        assertEquals(t3, history.get(2));
    }

    @Test
    void shouldMoveDuplicateTaskToTheEndAndNotDuplicate() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task t1 = new Task(1, "Task 1", "Desc 1", Status.NEW);
        Task t2 = new Task(2, "Task 2", "Desc 2", Status.NEW);
        Task t3 = new Task(3, "Task 3", "Desc 3", Status.NEW);

        // Добавили t1, t2, t3
        historyManager.add(t1);
        historyManager.add(t2);
        historyManager.add(t3);
        // Снова добавляем t1 (повтор)
        historyManager.add(t1);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size(), "Повтор t1 не должен увеличивать историю, а переместить t1 в конец");
        assertEquals(t2, history.get(0), "t2 теперь первый");
        assertEquals(t3, history.get(1), "t3 второй");
        assertEquals(t1, history.get(2), "t1 переместился в конец");
    }

    @Test
    void shouldRemoveTaskFromHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task t1 = new Task(1, "Task 1", "Desc 1", Status.NEW);
        Task t2 = new Task(2, "Task 2", "Desc 2", Status.NEW);
        Task t3 = new Task(3, "Task 3", "Desc 3", Status.NEW);

        historyManager.add(t1);
        historyManager.add(t2);
        historyManager.add(t3);

        historyManager.remove(t2.getId()); // Удаляем t2
        List<Task> history = historyManager.getHistory();

        assertEquals(2, history.size(), "История должна уменьшиться на одну задачу");
        assertFalse(history.contains(t2), "t2 должна быть удалена из истории");
        assertEquals(t1, history.get(0));
        assertEquals(t3, history.get(1));
    }

    @Test
    void shouldDoNothingWhenRemovingNonexistentId() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task t1 = new Task(1, "Task 1", "Desc 1", Status.NEW);
        historyManager.add(t1);

        historyManager.remove(999); // Нет задачи с таким id
        List<Task> history = historyManager.getHistory();

        assertEquals(1, history.size(), "История не должна измениться");
        assertTrue(history.contains(t1));
    }
}
