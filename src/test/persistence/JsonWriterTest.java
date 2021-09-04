package persistence;

import model.Todolist;
import model.exceptions.TaskTypeIncorrectException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            Todolist tl = new Todolist("My todolist");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterTodolist() {
        try {
            Todolist tl = new Todolist("My todolist");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTodolist.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTodolist.json");
            tl = reader.read();
            assertEquals("My todolist", tl.getName());
            assertEquals(0, tl.getNumberOfAllTask());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralTodolist() {
        try {
            Todolist tl = new Todolist("My todolist");
            tl.addTask("AAA", 0);
            tl.addTask("BBB", 1);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTodolist.json");
            writer.open();
            writer.write(tl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTodolist.json");
            tl = reader.read();
            assertEquals("My todolist", tl.getName());
            assertEquals(2, tl.getNumberOfAllTask());
            assertEquals(0, tl.getTask("AAA").getType());
            assertEquals(1, tl.getTask("BBB").getType());

        } catch (IOException | TaskTypeIncorrectException e) {
            fail("Exception should not have been thrown");
        }
    }

}
