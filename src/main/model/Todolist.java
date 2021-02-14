package model;

import java.util.ArrayList;
import java.util.List;

public class Todolist {

    ArrayList<Task> todolist;


    //MODIFIES: this
    //EFFECTS: construct a new todolist
    public Todolist() {
        todolist = new ArrayList<Task>();
    }

    //MODIFIES: this
    //EFFECTS: add a new task to the todolist
    public void addTask(String name, int type) {
        todolist.add(new Task(name, type));
    }

    //MODIFIES: this
    //EFFECTS: add a description to the given task
    public boolean addDescription(String name, String description) {
        boolean result = false;
        for (Task task : todolist) {
            if (task.getName().equals(name)) {
                task.setDescription(description);
                result = true;
                break;
            }
        }
        return result;
    }

    //REQUIRES: todolist is not empty
    //MODIFIES: this
    //EFFECTS: remove the given task from todolist and return true; otherwise return false
    public boolean removeTask(String taskToRemove) {
        boolean result = false;
        for (int i = 0; i < todolist.size(); i++) {
            if (taskToRemove.equals(todolist.get(i).getName())) {
                todolist.remove(i);
                result = true;
                break;
            }
        }
        return result;
    }


    //REQUIRES: given task is in the todolist
    //MODIFIES: this
    //EFFECTS: set the status of given task as completed and return true; otherwise return false
    public boolean completeTask(String taskToComplete) {
        boolean result = false;
        for (Task task: todolist) {
            if (taskToComplete.equals(task.getName())) {
                task.setStatus(2);
                result = true;
                break;
            }
        }
        return result;
    }

    //REQUIRES: given task is in the todolist
    //MODIFIES: this
    //EFFECTS: set the status of given as ongoing
    public boolean doingTask(String taskDoing) {
        boolean result = false;
        for (Task task: todolist) {
            if (taskDoing.equals(task.getName())) {
                task.setStatus(1);
                result = true;
                break;
            }

        }
        return result;
    }

    //EFFECTS: get the number of all tasks in the list
    public int getNumberOfAllTask() {
        return todolist.size();
    }

    //EFFECTS: get the number of incomplete tasks in the list
    public int getNumberOfIncompleteTask() {
        int count = 0;
        for (Task task : todolist) {
            if (task.getStatus() == 0 || task.getStatus() == 1) {
                count += 1;
            }
        }
        return count;
    }

    //EFFECTS: get the number of tasks completed in the list
    public int getNumberOfCompleteTask() {
        int count = 0;
        for (Task task : todolist) {
            if (task.getStatus() == 2) {
                count += 1;
            }
        }
        return count;
    }

    //EFFECTS: get the number of ongoing tasks in the list
    public int getNumberOfOngoingTask() {
        int count = 0;
        for (Task task : todolist) {
            if (task.getStatus() == 1) {
                count += 1;
            }
        }
        return count;
    }

    //EFFECTS: returns true if the todolist contains the task
    public boolean containTask(String name) {
        boolean result = false;
        for (int i = 0; i < todolist.size(); i++) {
            if (name.equals(todolist.get(i).getName())) {
                result = true;
                break;
            }
        }
        return result;
    }



    //EFFECTS: view complete and incomplete tasks
    public List<String> viewTasks(int status) {
        List<String> result = new ArrayList<String>();
        for (Task task: todolist) {
            if (status == task.getStatus()) {
                result.add(task.getName());
            }
        }
        return result;
    }

    //EFFECTS: view complete and incomplete tasks
    public Task getTask(String name) {
        Task result = new Task("",0);
        for (Task task: todolist) {
            if (name.equals(task.getName())) {
                result = task;
                break;
            }
        }
        return result;
    }



}
