package ru.netology.javacore;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TodoServer {
    private int port;
    public TodoServer(int port, Todos todos) {
        this.port = port;
    }

    public void start() {
        System.out.println("Starting server at " + port + "...");
        Todos todos = new Todos();
        Gson gson = new Gson();
        try (ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    String stringFromSocket = in.readLine();
                    if (stringFromSocket.equals(Constants.killSwitch)) {
                        break;
                    }

                    Action action = gson.fromJson(stringFromSocket, Action.class);
                    switch (action.getType()) {
                        case "ADD": {
                            todos.addTask(action.getTask());
                            break;
                        }
                        case "REMOVE": {
                            todos.removeTask(action.getTask());
                            break;
                        }
                        case "RESTORE": {
                            todos.restore();
                            break;
                        }
                        default:
                            break;
                    }
                    out.println(todos.getAllTasks());
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
