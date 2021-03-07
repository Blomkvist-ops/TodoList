package ui;

import model.Task;
import model.Todolist;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TodoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Todolist todolist;
    private Scanner input;

    // EFFECTS: runs todolist application
    public TodoListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        todolist = new Todolist("My todolist");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTodolist();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTodolist() {
        boolean keepGoing = true;
        String command;

        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();

            if (command.equals("quit")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");


    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("add")) {
            addTaskToList();
        } else if (command.equals("delete")) {
            deleteFromList();
        } else if (command.equals("complete")) {
            markComplete();
        } else if (command.equals("count incomplete")) {
            System.out.println(todolist.getNumberOfIncompleteTask());
        } else if (command.equals("count complete")) {
            System.out.println(todolist.getNumberOfCompleteTask());
        } else if (command.equals("add description")) {
            addListDescription();
        } else if (command.equals("view")) {
            viewTasks();
        } else if (command.equals("view single")) {
            viewSingleTask();
        } else if (command.equals("s")) {
            saveTodolist();
        } else if (command.equals("l")) {
            loadTodolist();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tadd -> add a new task");
        System.out.println("\tdelete -> delete a task");
        System.out.println("\tcount incomplete -> count incomplete");
        System.out.println("\tcount complete -> count complete");
        System.out.println("\tview -> view tasks");
        System.out.println("\tview single -> view a single task");
        System.out.println("\tcomplete -> mark a task as complete");
        System.out.println("\tadd description -> add description to a task");
        System.out.println("\ts -> save todolist to file");
        System.out.println("\tl -> load todolist from file");
        System.out.println("\tquit -> quit the application");
    }

    // MODIFIES: this
    // EFFECTS: add a new task to the todolist
    private void addTaskToList() {
        System.out.println("please input the title of the task:");
        String name = input.nextLine();
        System.out.println("please select the type of the task:");
        System.out.println("0 : important and urgent");
        System.out.println("1 : important, not urgent");
        System.out.println("2 : not important but urgent");
        System.out.println("3 : not important and not urgent");
        String type = input.nextLine();
        todolist.addTask(name, Integer.parseInt(type));
    }

    // MODIFIES: this
    // EFFECTS: delete a task from the todolist
    private void deleteFromList() {
        System.out.println("please input the title of the task you want to delete:");
        String name = input.nextLine();
        boolean result = todolist.removeTask(name);
        if (result) {
            System.out.println("The task has been deleted successfully.");
        } else {
            System.out.println("The list doesn't contain this task.");
        }

    }

    // MODIFIES: this
    // EFFECTS: mark a task as complete
    private void markComplete() {
        System.out.println("please input the title of the task you have completed:");
        String name = input.nextLine();
        boolean result = todolist.completeTask(name);
        if (result) {
            System.out.println("The task has been marked completed successfully.");
        } else {
            System.out.println("The list doesn't contain this task.");
        }

    }

    // MODIFIES: this
    // EFFECTS: add a description to a task in the list
    public void addListDescription() {
        System.out.println("please input the title of the task you want to add description to:");
        String name = input.nextLine();
        System.out.println("please input the description of the task you want to add:");
        String description = input.nextLine();
        boolean result = todolist.addDescription(name, description);
        if (result) {
            System.out.println("The description has been added successfully.");
        } else {
            System.out.println("The list doesn't contain this task.");
        }

    }

    // EFFECTS: view tasks of certain status
    public void viewTasks() {
        System.out.println("please input the tasks you want to view");
        System.out.println("0 : incomplete");
        System.out.println("1 : ongoing");
        System.out.println("2 : completed");
        String status = input.nextLine();
        System.out.println(todolist.viewTasks(Integer.parseInt(status)));

    }

    // EFFECTS: view tasks of certain status
    public void viewSingleTask() {
        System.out.println("please input the title of task you want to view");
        String name = input.nextLine();
        if (todolist.containTask(name)) {
            Task result = todolist.getTask(name);

            System.out.println("Task : " + name + ", Type: " + result.getType()
                    + ", Status: " + result.getStatus()
                    + ", Description: " + result.getDescription());
        } else {
            System.out.println("The todolist doesn't contain this task.");
        }

    }

    // EFFECTS: saves the todolist to file
    private void saveTodolist() {
        try {
            jsonWriter.open();
            jsonWriter.write(todolist);
            jsonWriter.close();
            System.out.println("Saved " + todolist.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads todolist from file
    private void loadTodolist() {
        try {
            todolist = jsonReader.read();
            System.out.println("Loaded " + todolist.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
