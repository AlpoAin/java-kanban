package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private TaskManager manager;  // Наш тестируемый менеджер (InMemoryTaskManager)

    @BeforeEach
    void setup() {
        // Managers.getDefault() возвращает новый InMemoryTaskManager
        manager = Managers.getDefault();
    }

    @Test
    void shouldAddAndGetTask() {
        Task task = new Task("Task 1", "Some description");
        manager.addTask(task);

        // Проверяем, что задача сохранилась и возвращается корректно
        Task retrieved = manager.getTask(task.getId());
        assertNotNull(retrieved, "Вернулась null вместо задачи");
        assertEquals(task.getId(), retrieved.getId(), "Id задачи не совпадает");
        assertEquals(task.getName(), retrieved.getName(), "Имя задачи не совпадает");
        assertEquals(task.getDescription(), retrieved.getDescription(), "Описание задачи не совпадает");
        assertEquals(task.getStatus(), retrieved.getStatus(), "Статус задачи не совпадает");
    }

    @Test
    void shouldReturnNullIfTaskNotFound() {
        // Запрос задачи с несуществующим id должен вернуть null
        assertNull(manager.getTask(999), "Ожидался null для несуществующей задачи");
    }

    @Test
    void shouldAddEpicWithoutSubtasks() {
        Epic epic = new Epic("Epic 1", "Epic description");
        manager.addEpic(epic);

        Epic retrievedEpic = manager.getEpic(epic.getId());
        assertNotNull(retrievedEpic, "Вернулся null вместо эпика");
        assertEquals(epic.getId(), retrievedEpic.getId(), "Id эпика не совпадает");
        assertTrue(retrievedEpic.getSubtaskIds().isEmpty(), "Список подзадач у нового эпика должен быть пуст");
    }

    @Test
    void shouldAddSubtaskAndLinkItToEpic() {
        // Создаём эпик и добавляем его
        Epic epic = new Epic("Epic 2", "Epic desc");
        manager.addEpic(epic);

        // Добавляем подзадачу, указывая epicId
        Subtask subtask = new Subtask("Subtask 1", "Sub desc", epic.getId());
        manager.addSubtask(subtask);

        // Проверяем, что подзадача вернулась корректно
        Subtask retrievedSubtask = manager.getSubtask(subtask.getId());
        assertNotNull(retrievedSubtask, "Подзадача не найдена по id");
        assertEquals(subtask.getId(), retrievedSubtask.getId(), "Id подзадачи не совпадает");

        // Проверяем, что в эпике действительно зарегистрирован id подзадачи
        Epic updatedEpic = manager.getEpic(epic.getId());
        assertTrue(updatedEpic.getSubtaskIds().contains(subtask.getId()),
                "Epic должен содержать id новой подзадачи");
    }

    @Test
    void shouldReturnNullIfSubtaskNotFound() {
        assertNull(manager.getSubtask(999), "Должны получить null для несуществующей подзадачи");
    }

    @Test
    void shouldTrackHistoryWithoutDuplicates() {
        // Добавляем задачи
        Task task = new Task("T1", "Desc1");
        manager.addTask(task);

        Epic epic = new Epic("Epic 3", "Epic desc 3");
        manager.addEpic(epic);

        Subtask subtask = new Subtask("S1", "Sub desc 1", epic.getId());
        manager.addSubtask(subtask);

        // Делаем несколько запросов: задача 2 раза, эпик 1 раз, подзадача 1 раз
        manager.getTask(task.getId());
        manager.getTask(task.getId()); // повторный
        manager.getEpic(epic.getId());
        manager.getSubtask(subtask.getId());

        // Проверяем историю
        List<Task> history = manager.getHistory();

        // В истории не должно быть дубликатов — значит, всего 3
        assertEquals(3, history.size(), "В истории должны быть task, epic, subtask (без дублей)");

        // Проверяем порядок: первый в истории — task (после повтора он не добавляется заново)
        assertEquals(task, history.get(0), "Первый в истории должен быть task");
        assertEquals(epic, history.get(1), "Второй в истории должен быть epic");
        assertEquals(subtask, history.get(2), "Третий в истории subtask");
    }

    @Test
    void shouldReturnHistoryWithCopiesIfRequested() {
        // Проверяем, что метод getHistory() действительно возвращает копии задач (как у тебя реализовано)

        Task task = new Task("Task 1", "Desc 1");
        manager.addTask(task);
        manager.getTask(task.getId()); // чтобы добавить в историю

        List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "Должна быть 1 задача в истории");
        Task historyTask = history.get(0);

        // Это должно быть не та же ссылка, ведь в твоём коде создаются новые объекты
        assertNotSame(task, historyTask, "Ожидалось, что в getHistory() будут копии объектов");
        // Но по значению (equals) они должны совпадать
        assertEquals(task, historyTask, "По equals задачи должны совпадать");
    }
}
