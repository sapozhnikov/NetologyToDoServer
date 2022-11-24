package ru.netology.javacore;

import java.util.*;
import java.util.stream.Collectors;

public class Todos {
    private ArrayList<String> todos;
    private Stack<Map.Entry<ActionType, String>> history;
    private final int MAX_TODOS = 7;

    public Todos(){
        todos = new ArrayList<>();
        history = new Stack<>();
    }

    public void addTask(String task) {
        if (todos.size() < MAX_TODOS) {
            todos.add(task);
        }
        history.push(new AbstractMap.SimpleEntry<>(ActionType.Add, task));
    }

    public void removeTask(String task) {
        todos.remove(task);
        history.push(new AbstractMap.SimpleEntry<>(ActionType.Remove, task));
    }

    public String getAllTasks() {
        return todos.stream().sorted().collect(Collectors.joining(" "));
    }

    public void restore() {
        while (true) {
            Map.Entry<ActionType, String> action;
            try {
                action = history.pop();
            }
            catch (EmptyStackException e) {
                break;
            }

            //if (action.getKey() == ActionType.Restore) { continue;}

            if (action.getKey() == ActionType.Add) {
                //removeTask(action.getValue());
                todos.remove(action.getValue());
                break;
            }

            if (action.getKey() == ActionType.Remove) {
                //addTask(action.getValue());
                todos.add(action.getValue());
                break;
            }

            break;
        }
    }
}
