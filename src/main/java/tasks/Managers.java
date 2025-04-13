package tasks;

public class Managers {
    private Managers() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    // Новый метод для истории
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
