import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW; // По умолчанию новая задача
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    // Переопределяем equals() по всем полям
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Task other = (Task) obj;
        return id == other.id
                && Objects.equals(name, other.name)
                && Objects.equals(description, other.description)
                && status == other.status;
    }

    // Переопределяем hashCode() по всем полям
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }

    // Переопределяем toString() для более красивого вывода
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
