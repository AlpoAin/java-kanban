package tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private static class Node {
        private Task task;   // Хранимая задача
        private Node prev;   // Ссылка на предыдущий узел
        private Node next;   // Ссылка на следующий узел

        public Node(Node prev, Task task, Node next) {
            this.prev = prev;
            this.task = task;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;

    private final Map<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return; // Ничего не делаем, если задача null
        }

        if (nodeMap.containsKey(task.getId())) {
            removeNode(nodeMap.get(task.getId()));
        }

        linkLast(task);
    }

    @Override
    public void remove(int id) {

        Node node = nodeMap.get(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;
        while (current != null) {
            history.add(current.task);
            current = current.next;
        }
        return history;
    }

    private void linkLast(Task task) {
        Node oldTail = tail;
        Node newNode = new Node(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        // Обновляем информацию в HashMap
        nodeMap.put(task.getId(), newNode);
    }

    private void removeNode(Node node) {
        final Node prev = node.prev;
        final Node next = node.next;

        if (prev == null) {
            head = next; // Удаляемый узел был head
        } else {
            prev.next = next;
        }
        if (next == null) {
            tail = prev; // Удаляемый узел был tail
        } else {
            next.prev = prev;
        }

        nodeMap.remove(node.task.getId());
    }
}
