package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


    HashMap<Integer, Node> history = new HashMap<>();

    Node first;
    Node last;

    @Override
    public void add(Task task) {
        Node node = history.get(task.getTaskId());
        if (node != null) {
            removeNode(node);
        }

        linkLast(task);

    }

    @Override
    public List<Task> getHistory() {
        ArrayList<Task> list = new ArrayList<>();
        Node current = first;

        while (current != null) {
            list.add(current.item);

            current = current.next;
        }
        return list;
    }


    @Override
    public void remove(int id) {
        Node node = history.get(id);
        removeNode(node);

    }



    void linkLast(Task task) {

        final Node l = last;
        final Node newNode = new Node(l, task, null);
        last = newNode;

        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }

        history.put(task.getTaskId(), newNode);

    }

    private void removeNode(Node node) {
        if (node == null) {
            return;
        }

        Node nextNode = node.next;
        Node prevNode = node.prev;

        if (prevNode == null) {
            first = nextNode;
        } else {
            prevNode.next = nextNode;
            node.prev = null;

        }

        if (nextNode == null) {
            last = prevNode;
        } else {
            nextNode.prev = prevNode;
            node.next = null;
        }

        history.remove(node.item.getTaskId());
        node.item = null;
    }

    private static class Node {

        Task item;
        Node next;
        Node prev;

        Node(Node prev, Task element, Node next) {

            this.item = element;
            this.next = next;
            this.prev = prev;

        }

    }


}