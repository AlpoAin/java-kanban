import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {
    // Хранит идентификаторы подзадач, принадлежащих данному эпику
    private final List<Integer> subtaskIds;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskIds = new ArrayList<>();
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public void removeSubtaskId(int id) {
        subtaskIds.remove(Integer.valueOf(id));
    }

    // Переопределяем equals() и hashCode() с учётом всех полей
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        // если базовые поля не совпадают — сразу false

        if (this.getClass() != obj.getClass()) return false;
        Epic other = (Epic) obj;
        return Objects.equals(subtaskIds, other.subtaskIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskIds);
    }

    // Переопределяем toString() для более наглядного отображения эпика
    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subtaskIds=" + subtaskIds +
                '}';
    }
}
