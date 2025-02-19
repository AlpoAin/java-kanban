import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private int nextId = 1; // счётчик для генерации уникальных идентификаторов

    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    // Генерация нового id
    private int generateId() {
        return nextId++;
    }

    // Создание обычной задачи
    public Task createTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    // Создание эпика
    public Epic createEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return epic;
    }

    // Создание подзадачи
    public Subtask createSubtask(Subtask subtask) {
        int id = generateId();
        subtask.setId(id);
        subtasks.put(id, subtask);

        // Добавляем подзадачу в список подзадач соответствующего эпика
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtaskId(id);
            updateEpicStatus(epic.getId());
        }
        return subtask;
    }

    // Получение задачи по id
    public Task getTask(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    }

    // Получение списков всех задач
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    // Получение списка подзадач для конкретного эпика
    public List<Subtask> getSubtasksByEpic(int epicId) {
        List<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    result.add(subtask);
                }
            }
        }
        return result;
    }

    // Обновление задачи
    public Task updateTask(Task task) {
        int id = task.getId();
        if (tasks.containsKey(id)) {
            tasks.put(id, task);
            return task;
        }
        return null;
    }

    // Обновление эпика (пользователь не может напрямую менять статус эпика)
    public Epic updateEpic(Epic epic) {
        int id = epic.getId();
        if (epics.containsKey(id)) {
            Epic oldEpic = epics.get(id);

            // Обновляем только поля, которые пользователь действительно может менять
            oldEpic.name = epic.getName();
            oldEpic.description = epic.getDescription();

            // Статус эпика пересчитывается автоматически
            updateEpicStatus(id);
            return oldEpic;
        }
        return null;
    }

    // Обновление подзадачи и автоматическое обновление статуса соответствующего эпика
    public Subtask updateSubtask(Subtask subtask) {
        int id = subtask.getId();
        if (subtasks.containsKey(id)) {
            subtasks.put(id, subtask);
            updateEpicStatus(subtask.getEpicId());
            return subtask;
        }
        return null;
    }

    // Удаление задачи
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    // Удаление эпика и всех его подзадач
    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            // Удаляем подзадачи, принадлежащие этому эпику
            for (Integer subtaskId : new ArrayList<>(epic.getSubtaskIds())) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    // Удаление подзадачи с обновлением эпика
    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic.getId());
            }
            subtasks.remove(id);
        }
    }

    // Удаление всех задач одного типа
    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear(); // при удалении всех эпиков удаляем и все подзадачи
    }

    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    // Метод для обновления статуса эпика на основе статусов его подзадач
    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        List<Subtask> epicSubtasks = getSubtasksByEpic(epicId);

        // Если у эпика нет подзадач, статус — NEW
        if (epicSubtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
            }
            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epic.setStatus(Status.NEW);
        } else if (allDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
