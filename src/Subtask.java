import java.util.Objects;

public class Subtask extends Task {
    // Идентификатор эпика, в рамках которого выполняется подзадача
    private int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        // если базовые поля не совпадают — сразу false

        if (this.getClass() != obj.getClass()) return false;
        Subtask other = (Subtask) obj;
        return epicId == other.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    // Переопределяем toString() для более наглядного отображения подзадачи
    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", epicId=" + epicId +
                '}';
    }
}
