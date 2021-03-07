package persistence;

import model.Todolist;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Todolist tl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTodolist() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTodolist.json");
        try {
            Todolist tl = reader.read();
            assertEquals("My todolist", tl.getName());
            assertEquals(0, tl.getNumberOfAllTask());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTodolist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTodolist.json");
        try {
            Todolist tl = reader.read();
            assertEquals("My todolist", tl.getName());
            assertEquals(2, tl.getNumberOfAllTask());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
