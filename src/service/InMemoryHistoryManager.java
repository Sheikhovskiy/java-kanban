package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


    HashMap<Integer, Node> history = new HashMap<>();

    Node first;
    Node last;

    @Override
    public void add(Task task) {
        Node node = history.get(task.getTaskId());
        //removeNode(node);
        linkLast(task);

    }

    @Override
    public List<Task> getHistory(){
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
        history.remove(node.item.getTaskId());

    }



    void linkLast (Task task) {

        final Node l = last;
        final Node newNode = new Node(l, task, null);
        last = newNode;

        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }

    }

    private void removeNode(Node node) {
        history.remove(node.item.getTaskId());
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