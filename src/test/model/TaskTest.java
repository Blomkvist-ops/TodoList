package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;

    @Test
    public void testConstructor() {
       task = new Task("test task",0);
       task.setHours(3);
       task.setDeadline(2021,3,1);
       task.setDescription("this is a simple task");
       assertEquals(0,task.getType());
       task.setType(2);
       assertEquals(2,task.getType());
       assertEquals("test task", task.getName());
       assertEquals("this is a simple task", task.getDescription());
       assertEquals(3, task.getHours());
       assertEquals(2021, task.getDeadline().get(task.getDeadline().YEAR));
       assertEquals(3, task.getDeadline().get(task.getDeadline().MONTH));
       assertEquals(1, task.getDeadline().get(task.getDeadline().DATE));

    }




}