package ru.netology.javacore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TodosTests {
    private final int MAX_TODOS = 7;
    @Test
    void TasksLimitTest() {
        Todos todos = new Todos();
        todos.addTask("task1");
        todos.addTask("task2");
        todos.addTask("task3");
        todos.addTask("task4");
        todos.addTask("task5");
        todos.addTask("task6");
        todos.addTask("task7");
        todos.addTask("task8");
        todos.addTask("task9");
        String allTasks = todos.getAllTasks();
        String[] tasks = allTasks.split(" ");
        Assertions.assertTrue(tasks.length <= MAX_TODOS);
    }

    @Test
    void TaskSortedTest() {
        Todos todos = new Todos();
        todos.addTask("jP2vpRmFFZ");
        todos.addTask("3redc8TNeJ");
        todos.addTask("Yz1Lm5wivr");
        todos.addTask("CStl6oyMUt");
        todos.addTask("imw2GmNLTy");
        String allTasks = todos.getAllTasks();
        String[] tasks = allTasks.split(" ");
        for (int i = 0; i < tasks.length - 1; i++) {
            Assertions.assertTrue(tasks[i].compareTo(tasks[i+1]) < 0);
        }
    }

    @Test
    void TaskDeleteTest() {
        Todos todos = new Todos();
        todos.addTask("task1");
        todos.addTask("task2");
        todos.addTask("task3");
        todos.addTask("task4");
        todos.addTask("task5");
        todos.addTask("task6");
        todos.removeTask("task3");
        todos.removeTask("task1");
        String allTasks = todos.getAllTasks();
        List<String> tasks = List.of(allTasks.split(" "));
        Assertions.assertFalse(tasks.contains("task3"));
        Assertions.assertFalse(tasks.contains("task1"));
        Assertions.assertTrue(tasks.contains("task2"));
        Assertions.assertTrue(tasks.contains("task6"));
    }

    @Test
    void TaskRestoreTest() {
        Todos todos = new Todos();
        todos.addTask("Первая");
        todos.addTask("Вторая");
        todos.removeTask("Первая");
        todos.addTask("Третья");
        todos.restore();
        todos.restore();
        String allTasks = todos.getAllTasks();
        Assertions.assertTrue(allTasks.equals("Вторая Первая"));
    }
}
