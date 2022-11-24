package ru.netology.javacore;

public class TodoServerThread extends Thread {
    private TodoServer server;

    public TodoServerThread(int port, Todos todos) {
        server = new TodoServer(port, todos);
    }

    public TodoServer getServer() {
        return server;
    }

    @Override
    public void run() {
        server.start();
    }
}
