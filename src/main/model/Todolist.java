package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Todolist implements Writable {

    String listName;
    ArrayList<Task> todolist;


    //MODIFIES: this
    //EFFECTS: construct a new todolist
    public Todolist(String listName) {
        this.listName = listName;
        todolist = new ArrayList<>();
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
        for (Task task : todolist) {
            if (name.equals(task.getName())) {
                result = true;
                break;
            }
        }
        return result;
    }



    //EFFECTS: view complete and incomplete tasks
    public List<String> viewTasks(int status) {
        List<String> result = new ArrayList<>();
        for (Task task: todolist) {
            if (status == task.getStatus()) {
                result.add(task.getName());
            }
        }
        return result;
    }

    //EFFECTS: view complete and incomplete tasks
    public Task getTask(String name) {
        Task result = new Task(null,0);
        for (Task task: todolist) {
            if (!Objects.equals(name, task.getName())) {
                continue;
            } else {
                result = task;
                break;
            }

        }
        return result;
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("listName", listName);
        json.put("todolist", tasksToJson());
        return json;
    }

    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t : todolist) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

    public String getName() {
        return listName;
    }

    // MODIFIES: this
    // EFFECTS: replace the name of to-do list with a new name
    public void changeName(String name) {
        this.listName = name;
    }

    // EFFECTS: return the list of tasks in the to-do list
    public ArrayList<Task> getTasks() {
        return todolist;
    }
}
