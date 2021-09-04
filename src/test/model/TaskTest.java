package model;


import model.exceptions.TaskTypeIncorrectException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    Task task;

    @Test
    public void testConstructor() {
        try {
            task = new Task("test task", 0);
        } catch (TaskTypeIncorrectException e) {
            fail("catch a task type exception");
        }
        task.setDescription("this is a simple task");
        assertEquals(0, task.getType());
        assertEquals(0, task.getStatus());
        task.setType(2);
        task.setStatus(1);
        assertEquals(2, task.getType());
        assertEquals(1, task.getStatus());
        assertEquals("test task", task.getName());
        assertEquals("this is a simple task", task.getDescription());


    }


    @Test
    public void testTypeException() {
        try {
            Task task3 = new Task("test", 4);
        } catch (TaskTypeIncorrectException e) {
            //
        }
        try {
            Task task4 = new Task("aaa", -1);
        } catch (TaskTypeIncorrectException e) {
            //
        }
        try {
            Task task5 = new Task("aaa", 0);
        } catch (TaskTypeIncorrectException e) {
            fail("catch a task name exception");
        }
        try {
            Task task6 = new Task("bbb", 3);
        } catch (TaskTypeIncorrectException e) {
            fail("catch a task name exception");
        }
    }


}