package model;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;

    @Test
    public void testConstructor() {
       task = new Task("test task",0);
       task.setDescription("this is a simple task");
       assertEquals(0,task.getType());
        assertEquals(0,task.getStatus());
       task.setType(2);
       task.setStatus(1);
       assertEquals(2,task.getType());
        assertEquals(1,task.getStatus());
       assertEquals("test task", task.getName());
       assertEquals("this is a simple task", task.getDescription());


    }




}