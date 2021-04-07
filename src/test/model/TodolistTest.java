package model;

import model.exceptions.TaskTypeIncorrectException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TodolistTest {

    Todolist todolist;

    @BeforeEach
    public void runBefore() {
        todolist = new Todolist("Testlist");
        try {
            todolist.addTask("AAA", 0);
        } catch (TaskTypeIncorrectException e) {
            fail("incorrect type");
        }
        try {
            todolist.addTask("BBB", 1);
        } catch (TaskTypeIncorrectException e) {
            fail("incorrect type");
        }
        try {
            todolist.addTask("CCC", 2);
        } catch (TaskTypeIncorrectException e) {
            fail("incorrect type");
        }
        try {
            todolist.addTask("DDD", 3);
        } catch (TaskTypeIncorrectException e) {
            fail("incorrect type");
        }
    }

    @Test
    public void testListName() {
        assertEquals("Testlist", todolist.getName());
    }

    @Test
    public void testAddTask() {
        try {
            todolist.addTask("FFF", 2);
        } catch (TaskTypeIncorrectException e) {
            fail("incorrect type");
        }
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
        result = todolist.completeTask("ZZZ");
        assertFalse(result);

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
        try {
            assertEquals("This is a nice task", todolist.getTask("AAA").getDescription());
        } catch (TaskTypeIncorrectException e) {
            fail("incorrect type");
        }
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

    @Test
    public void testContainTask() {
        assertTrue(todolist.containTask("AAA"));
        assertFalse(todolist.containTask("MMM"));
    }

    @Test
    public void testGetTask() {
        try {
            assertEquals("BBB", todolist.getTask("BBB").getName());
        } catch (TaskTypeIncorrectException e) {
            fail("incorrect type");
        }
        try {
            assertEquals(null, todolist.getTask("ZZZ").getName());
        } catch (TaskTypeIncorrectException e) {
            fail("incorrect type");
        }
    }



}
