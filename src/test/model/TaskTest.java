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
       task.setType(2);
       assertEquals(2,task.getType());
       assertEquals("test task", task.getName());
       assertEquals("this is a simple task", task.getDescription());


    }




}