package persistence;

import model.Todolist;
import model.exceptions.TaskTypeIncorrectException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.lang.Integer.valueOf;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads todolist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Todolist read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodolist(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses todolist from JSON object and returns it
    private Todolist parseTodolist(JSONObject jsonObject) {
        String name = jsonObject.getString("listName");
        Todolist tl = new Todolist(name);
        addTasks(tl, jsonObject);
        return tl;
    }

    // MODIFIES: tl
    // EFFECTS: parses thingies from JSON object and adds them to todolist
    private void addTasks(Todolist tl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("todolist");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            try {
                addTask(tl, nextTask);
            } catch (TaskTypeIncorrectException e) {
                e.printStackTrace();
            }
        }
    }

    // MODIFIES: tl
    // EFFECTS: parses thingy from JSON object and adds it to todolist
    private void addTask(Todolist tl, JSONObject jsonObject) throws TaskTypeIncorrectException {
        String name = jsonObject.getString("name");
        int type = valueOf(jsonObject.getString("type"));
        if (!(type == 0 || type == 1 || type == 2 || type == 3)) {
            throw new TaskTypeIncorrectException("incorrect type");
        } else {
            tl.addTask(name, type);
        }

    }
}
