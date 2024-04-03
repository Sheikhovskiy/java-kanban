package service;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {


/*    LinkedList<Task> history = new LinkedList<>();
    @Override
    public void add(Task task) {

        if (history.size() > 9) {
            history.removeFirst();
        }
        history.add(task);

    }*/
//    @Override
//    public LinkedList<Task> getHistory() {
//        return history;
//    }



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


    HashMap<Integer, Node> history = new HashMap<>();

    Node first;
    Node last;

    @Override
    public void add(Task task) {
        Node node = history.get(task.getTaskId());
//        if (node != null) {
//            removeNode(node);
//            linkLast(task);
//        }
//        history.put(task.getTaskId(), node);

        //removeNode(node);
        linkLast(task);

    }

    public List<Task> getHistory(){
        ArrayList<Task> list = new ArrayList<>();
        Node current = first;

        while (current != null) {
            list.add(current.item);

            current = current.next;
        }
        return list;
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


}