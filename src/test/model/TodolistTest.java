package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodolistTest {

    Todolist todolist;

    @BeforeEach
    public void runBefore() {
        todolist = new Todolist();
        todolist.addTask("AAA", 0);
        todolist.addTask("BBB", 1);
        todolist.addTask("CCC", 2);
        todolist.addTask("DDD", 3);
    }

    @Test
    public void testAddTask() {
        todolist.addTask("FFF", 2);
        assertEquals(5, todolist.getNumberOfAllTask());
    }

    @Test
    public void testRemoveTask() {
        boolean result;
        result = todolist.removeTask("AAA");
        assertTrue(result);
        assertEquals(3,todolist.getNumberOfAllTask());
        result = todolist.removeTask("AAA");
        assertFalse(result);
        assertFalse(todolist.containTask("AAA"));
    }

    @Test
    public void testCompleteTask() {
        assertEquals(4,todolist.getNumberOfIncompleteTask());
        boolean result = todolist.completeTask("BBB");
        assertEquals(3,todolist.getNumberOfIncompleteTask());
        assertEquals(1,todolist.getNumberOfCompleteTask());
        assertTrue(result);

    }

    @Test
    public void testDoingTask() {
        assertEquals(4,todolist.getNumberOfIncompleteTask());
        assertEquals(0,todolist.getNumberOfOngoingTask());
        boolean result1 = todolist.doingTask("CCC");
        boolean result2 = todolist.doingTask("AAA");
        boolean result3 = todolist.doingTask("ZZZ");
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
        assertEquals(4,todolist.getNumberOfIncompleteTask());
        assertEquals(2,todolist.getNumberOfOngoingTask());
    }

    @Test
    public void testGetTypeTasks() {
        todolist.addTask("EEE",1);
        todolist.addTask("FFF",3);
        todolist.addTask("GGG",0);
        todolist.addTask("HHH",1);
        List result = todolist.getTypeTasks(1);
        assertEquals(3, result.size());
        assertTrue(result.contains("BBB"));
        assertTrue(result.contains("EEE"));
        assertTrue(result.contains("HHH"));

    }

    @Test
    public void testViewTasks() {
        List result = todolist.viewTasks(0);
        assertEquals(4, result.size());
        todolist.completeTask("AAA");
        result = todolist.viewTasks(0);
        assertEquals(3, result.size());
        assertFalse(result.contains("AAA"));
    }


    @Test
    public void testAddDescription() {
        boolean result1 = todolist.addDescription("AAA", "This is a nice task");
        boolean result2 = todolist.addDescription("MMM", "this task doesn't exist");
        assertTrue(result1);
        assertFalse(result2);
        assertEquals("This is a nice task", todolist.getTask("AAA").getDescription());
    }

    @Test
    public void testGetNumberTask() {
        assertEquals(4, todolist.getNumberOfAllTask());
        assertEquals(4, todolist.getNumberOfIncompleteTask());
        assertEquals(0, todolist.getNumberOfCompleteTask());
        todolist.completeTask("AAA");
        todolist.removeTask("BBB");
        assertEquals(3, todolist.getNumberOfAllTask());
        assertEquals(2, todolist.getNumberOfIncompleteTask());
        assertEquals(1, todolist.getNumberOfCompleteTask());
    }



}
