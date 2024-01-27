package model;

import service.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    public ArrayList<Subtask> subtasks = new ArrayList<>();
    //public HashMap<Integer, Subtask> epics = new HashMap<>();



    public Epic(String taskName, String taskDescription, String status) {

        super(taskName, taskDescription, status);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }


//    public void addTask(Subtask subtask) {
//        epics.put(taskManager.generateId(), subtask);
//    }




    /*    public void addTask(Subtask subtask) {

        }

        public void removeTask(Subtask subtask) {

        }*/

}
