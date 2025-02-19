public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // 1. Создаём две обычные задачи
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        manager.createTask(task1);

        Task task2 = new Task("Задача 2", "Описание задачи 2");
        manager.createTask(task2);

        // 2. Создаём эпик с двумя подзадачами
        Epic epic1 = new Epic("Эпик 1", "Эпик с двумя подзадачами");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic1.getId());
        manager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic1.getId());
        manager.createSubtask(subtask2);

        // 3. Создаём эпик с одной подзадачей
        Epic epic2 = new Epic("Эпик 2", "Эпик с одной подзадачей");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", epic2.getId());
        manager.createSubtask(subtask3);

        // 4. Распечатываем списки всех задач
        System.out.println("=== Начальный список задач ===");
        System.out.println("Обычные задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());

        // 5. Изменяем статусы созданных объектов
        // - Сделаем первую задачу выполненной (DONE)
        task1.setStatus(Status.DONE);
        manager.updateTask(task1);

        // - Подзадача subtask1 тоже выполнена
        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);

        // - Подзадачу subtask3 переведём в статус IN_PROGRESS
        subtask3.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask3);

        // Повторно распечатываем, чтобы увидеть изменения статусов
        System.out.println("\n=== После изменения статусов ===");
        System.out.println("Обычные задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());

        // 6. Удаляем одну задачу и один эпик
        manager.deleteTask(task2.getId()); // Удаляем вторую задачу
        manager.deleteEpic(epic1.getId()); // Удаляем эпик с двумя подзадачами

        // 7. Итоговое состояние
        System.out.println("\n=== Итоговый список после удаления ===");
        System.out.println("Обычные задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());
    }
}
