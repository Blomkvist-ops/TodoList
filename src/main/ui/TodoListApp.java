package ui;

import model.Task;
import model.Todolist;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Scanner input;
    private JFrame frame;
    private Todolist todolist = new Todolist("my todolist");
    private JPanel mainPanel = new JPanel();
    private static final Integer WIDTH = 800;
    private static final Integer HEIGHT = 500;
    private JTextField nameText = new JTextField(20);
    private JTextField  typeText = new JTextField(20);
    private JTextArea detailText = new JTextArea();
    private JButton btnAdd = new JButton("Add");    //创建JButton对象
    private JButton btnDel = new JButton("Delete");
    private JButton btnSave = new JButton("Save");
    private JButton btnLoad = new JButton("Load");
    private JButton btnView = new JButton("View");

    // EFFECTS: runs todolist application
    public TodoListApp() throws FileNotFoundException {
        init();
        createGui();

    }

    // MODIFIES: this
    // EFFECTS: initializes the to-do list and the menu bar
    private void init() {
        input = new Scanner(System.in);
        todolist = new Todolist("My todolist");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

    }


    // MODIFIES: this
    // EFFECTS: create a Gui Frame
    public void createGui() {
        frame = new JFrame("Todo List Application");
        frame.setSize(WIDTH,HEIGHT);    //设置窗口显示尺寸
        JLabel title = new JLabel(todolist.getName());
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //置窗口是否可以关闭
        //Container c = frame.getContentPane();    //获取当前窗口的内容窗格
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(null);
        setButtons();
        frame.add(mainPanel);
        frame.setVisible(true);    //设置窗口是否可见

    }

    // MODIFIES: this
    // EFFECTS: set the positions of buttons of a Gui Frame
    public void setButtons() {
        btnAdd.setBounds(300,100,200,35);
        btnDel.setBounds(300,150,200,35);
        btnSave.setBounds(300,200,200,35);
        btnLoad.setBounds(300,250,200,35);
        btnView.setBounds(300, 300,200,35);
        mainPanel.add(btnAdd);
        mainPanel.add(btnDel);
        mainPanel.add(btnSave);
        mainPanel.add(btnLoad);
        mainPanel.add(btnView);
        btnAdd.addActionListener(e -> addATask());
        btnDel.addActionListener(e -> deleteATask());
        btnSave.addActionListener(e -> saveTasks());
        btnLoad.addActionListener(e -> loadTasks());
        btnView.addActionListener(e -> viewTaskPanel());
    }



    // MODIFIES: this
    // EFFECTS: get the panel of viewing tasks by type
    public void viewTaskPanel() {
        JFrame viewTaskFrame = new JFrame("View tasks by type");
        viewTaskFrame.setLocationRelativeTo(frame);
        JButton nextButton = new JButton("Next");
        JButton backButton = new JButton("Cancel");
        viewTaskFrame.setLayout(new BorderLayout());
        nextButton.addActionListener(e -> {
            viewTasksByType(typeText.getText());
            viewTaskFrame.dispose();
        });
        backButton.addActionListener(e -> viewTaskFrame.dispose());
        JPanel lowerPanel = new JPanel();
        JPanel centerPanel2 = new JPanel();
        setViewTaskCenterPanel(centerPanel2);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerPanel.add(nextButton);
        lowerPanel.add(backButton);
        viewTaskFrame.add(centerPanel2,BorderLayout.CENTER);
        viewTaskFrame.add(lowerPanel,BorderLayout.SOUTH);
        viewTaskFrame.setSize(350,200);
        viewTaskFrame.setVisible(true);

    }


    public void playSound(String choice) {
        File errorAudio = new File("./data/errorSound.wav");
        File clickAudio = new File("./data/clickSound.wav");
        File popOutAudio = new File("./data/popOutSound.wav");
        URL url = null;
        try {
            switch (choice) {
                case "error":
                    url = errorAudio.toURI().toURL();
                    break;
                case "click":
                    url = clickAudio.toURI().toURL();
                    break;
                case "popOut":
                    url = popOutAudio.toURI().toURL();
                    break;
            }
            AudioClip sound = Applet.newAudioClip(url);
            sound.play();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: get the panel of viewing tasks by type
    public JPanel setViewTaskCenterPanel(JPanel centerPanel) {
        JLabel typeLabel = new JLabel("Type");
        centerPanel.setLayout(null);
        typeText.setBounds(100,45,165,20);
        typeLabel.setBounds(10,45,165,20);
        centerPanel.add(typeText);
        centerPanel.add(typeLabel);
        return centerPanel;
    }


    // MODIFIES: this
    // EFFECTS: get the panel of viewing tasks by type
    public void viewTasksByType(String type) {
        JFrame viewTaskFrame = new JFrame("View Tasks");
        JLabel viewTaskLabel = getViewTaskLabel(type);
        viewTaskLabel.setHorizontalAlignment(SwingConstants.CENTER);
        viewTaskFrame.setLocationRelativeTo(frame);
        viewTaskFrame.setLayout(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> viewTaskFrame.dispose());
        JPanel centerPanel = getToDoListPanelByStatus(type);
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerPanel.add(backButton);
        viewTaskFrame.add(viewTaskLabel,BorderLayout.NORTH);
        viewTaskFrame.add(centerPanel,BorderLayout.CENTER);
        viewTaskFrame.add(lowerPanel,BorderLayout.SOUTH);
        viewTaskFrame.setSize(600,350);
        viewTaskFrame.setVisible(true);
    }


    // MODIFIES: JPanel
    // EFFECTS: return a JPanel containing a table of all incomplete or all complete tasks
    public JPanel getToDoListPanelByStatus(String type) {
        JPanel panel = new JPanel();
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel) { public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"Name","Description","Type"});
        ArrayList<Task> tasks = todolist.getTasks();
        int intType = Integer.valueOf(type).intValue();
        tasks.forEach(task -> {
            if (intType == task.getType()) {
                tableModel.addRow(new Object[]{task.getName(),task.getDescription(), task.getType()});
            }
        });
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        panel.add(new JScrollPane(table) {
            public Dimension getPreferredSize() {
                return new Dimension(450, 250);
            }
        });
        return panel;
    }


    // MODIFIES: JLabel
    // EFFECTS: return a JLabel of given type
    public JLabel getViewTaskLabel(String type) {
        JLabel viewTaskLabel = new JLabel("view tasks.");
        if (type == "0") {
            viewTaskLabel.setText("Important and urgent");
        } else if (type == "1") {
            viewTaskLabel.setText("Important but not urgent");
        } else if (type == "2") {
            viewTaskLabel.setText("not important but urgent");
        } else if (type == "3") {
            viewTaskLabel.setText("not important and not urgent");
        }

        return viewTaskLabel;
    }


    // EFFECTS: load tasks from previous file
    public void loadTasks() {
        playSound("popOut");
        JPanel panel = new JPanel();
        loadTodolist();
        //JOptionPane.showMessageDialog(panel,"Loaded " + todolist.getName() + " from " + JSON_STORE,
        //       "Successfully Loaded", JOptionPane.INFORMATION_MESSAGE);
        showTaskFrame();
    }


    // MODIFIES: this
    // EFFECTS: set the frame of loading tasks
    public void showTaskFrame() {
        JFrame loadFrame = new JFrame("Load all tasks");
        loadFrame.setLocationRelativeTo(frame);
        JButton backButton = new JButton("Cancel");
        loadFrame.setLayout(new BorderLayout());
        backButton.addActionListener(e -> loadFrame.dispose());
        JPanel lowerPanel =  new JPanel();
        JPanel centerPanel = getToDoListPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerPanel.add(backButton);
        loadFrame.add(centerPanel,BorderLayout.CENTER);
        loadFrame.add(lowerPanel,BorderLayout.SOUTH);
        loadFrame.setSize(650,323);
        loadFrame.setVisible(true);
    }


    // MODIFIES: this, JPanel
    // EFFECTS: return the center panel of the main frame, containing a table of to-do list
    public JPanel getToDoListPanel() {
        JPanel panel = new JPanel();
        if (todolist.getNumberOfAllTask() == 0) {
            JLabel label = new JLabel("There is no task yet.");
            panel.add(label);
        } else {
            JTable table = getTaskTable();
            panel.add(new JScrollPane(table) {
                public Dimension getPreferredSize() {
                    return new Dimension(650, 323);
                }
            });
        }
        return panel;
    }


    // MODIFIES: this
    // EFFECTS: return a JTable of all to-do list

    public JTable getTaskTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[]{"Name","Description","Type"});
        ArrayList<Task> tasks = todolist.getTasks();
        tasks.forEach(task -> tableModel.addRow(new Object[]{task.getName(),
                task.getDescription(),task.getType()}));
        table.setRowHeight(30);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

        return table;
    }


    // MODIFIES: this
    // EFFECTS: save todolist to a json file
    public void saveTasks() {
        playSound("popOut");
        JOptionPane savePanel = new JOptionPane();
        int save = JOptionPane.showConfirmDialog(savePanel,
                "Going to save the list with name \"" + todolist.getName() + "\". Do you confirm?",
                "Save Confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (save == 0) {
            playSound("click");
            saveTodolist();
            JOptionPane.showMessageDialog(savePanel,"Saved " + todolist.getName() + " to " + JSON_STORE,
                    "Successfully Saved", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // MODIFIES: this
    // EFFECTS: delete a task by name
    public void deleteATask() {
        JFrame deleteTaskFrame = new JFrame("Delete a task");
        deleteTaskFrame.setLocationRelativeTo(frame);
        JButton nextButton = new JButton("Next");
        JButton backButton = new JButton("Cancel");
        deleteTaskFrame.setLayout(new BorderLayout());
        nextButton.addActionListener(e -> {
            deleteAPickedTask(nameText.getText());
            deleteTaskFrame.dispose();
        });
        backButton.addActionListener(e -> deleteTaskFrame.dispose());
        JPanel lowerPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        setDeleteCenterPanel(centerPanel);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerPanel.add(nextButton);
        lowerPanel.add(backButton);
        deleteTaskFrame.add(centerPanel,BorderLayout.CENTER);
        deleteTaskFrame.add(lowerPanel,BorderLayout.SOUTH);
        deleteTaskFrame.setSize(350,200);
        deleteTaskFrame.setVisible(true);

    }

    // MODIFIES: this, JLabel
    // EFFECTS: set the panel of deleting tasks
    public JPanel setDeleteCenterPanel(JPanel centerPanel) {
        JLabel nameLabel = new JLabel("Delete Name");
        centerPanel.setLayout(null);
        nameText.setBounds(100,20,165,20);
        nameLabel.setBounds(10,20,165,20);
        centerPanel.add(nameText);
        centerPanel.add(nameLabel);
        return centerPanel;
    }

    // MODIFIES: this
    // EFFECTS: delete a task by given name
    public void deleteAPickedTask(String name) {
        try {

            todolist.removeTask(name);

        } catch (Exception e) {
            System.out.println("error");
        }

        refreshFrame();

    }

    // MODIFIES: this
    // EFFECTS: add a new task to todolist
    public void addATask() {
        JFrame addTaskFrame = new JFrame("Add a task");
        addTaskFrame.setLocationRelativeTo(frame);
        JButton nextButton = new JButton("Next");
        JButton backButton = new JButton("Cancel");
        addTaskFrame.setLayout(new BorderLayout());
        nextButton.addActionListener(e -> {
            addANewTask(nameText.getText(), typeText.getText(), detailText.getText());
            addTaskFrame.dispose();
        });
        backButton.addActionListener(e -> addTaskFrame.dispose());
        JPanel lowerPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        setAddCenterPanel(centerPanel);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        lowerPanel.add(nextButton);
        lowerPanel.add(backButton);
        addTaskFrame.add(centerPanel,BorderLayout.CENTER);
        addTaskFrame.add(lowerPanel,BorderLayout.SOUTH);
        addTaskFrame.setSize(350,200);
        addTaskFrame.setVisible(true);

    }

    // MODIFIES: this, JPanel
    // EFFECTS: set the panel of adding a task
    public JPanel setAddCenterPanel(JPanel centerPanel) {
        setTextField();
        JLabel nameLabel = new JLabel("Name");
        JLabel typeLabel = new JLabel("Type");
        JLabel descriptionLabel = new JLabel("Description");
        centerPanel.setLayout(null);
        nameText.setBounds(100,20,165,20);
        typeText.setBounds(100,45,165,20);
        detailText.setBounds(100,70,165,50);
        nameLabel.setBounds(10,20,165,20);
        typeLabel.setBounds(10,45,165,20);
        descriptionLabel.setBounds(10,70,165,50);
        centerPanel.add(nameText);
        centerPanel.add(typeText);
        centerPanel.add(detailText);
        centerPanel.add(nameLabel);
        centerPanel.add(typeLabel);
        centerPanel.add(descriptionLabel);
        return centerPanel;
    }

    // MODIFIES: this
    // EFFECTS: add a new task to todolist
    public void addANewTask(String name, String type, String description) {
        try {
            int intType = Integer.valueOf(type).intValue();
            todolist.addTask(name, intType);
            todolist.addDescription(name,description);
        } catch (NumberFormatException e) {
            System.out.println("format error");
        }

        refreshFrame();


    }

    // MODIFIES: this
    // EFFECTS: set the range of a text field
    public void setTextField() {

        detailText.setColumns(20);
        detailText.setRows(3);
    }

    public void refreshFrame() {
        frame.dispose();
        createGui();
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
