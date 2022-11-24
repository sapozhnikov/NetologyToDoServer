package ru.netology.javacore;

import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.fail;

public class TodoServerTests {
    private static Thread serverThread;
    private static Todos todos;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    @BeforeAll
    static void ServerInit() throws InterruptedException {
        todos = new Todos();
        serverThread = new TodoServerThread(8989, todos);
        serverThread.setPriority(java.lang.Thread.NORM_PRIORITY + 1);
        serverThread.start();
        Thread.sleep(100); //I know, I know, no time to fix
    }
    @BeforeEach
    void ServerStart() throws IOException {
        socket = new Socket("localhost", 8989);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socket.setSoTimeout(1000);
    }

    @Test
    void DataAccept1() {
        System.out.println("sending test packet");
        out.println("{ \"type\": \"ADD\", \"task\": \"Первая\" }");
        String response = "";
        try {
            response = in.readLine();
            System.out.println("client received: " + response);
        } catch (SocketTimeoutException e) {
            fail("No response from server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(response.equals("Первая"));
    }

    @Test
    void DataAccept2() {
        System.out.println("sending test packet");
        out.println("{ \"type\": \"ADD\", \"task\": \"Вторая\" }");
        String response = "";
        try {
            response = in.readLine();
            System.out.println("client received: " + response);
        } catch (SocketTimeoutException e) {
            fail("No response from server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(response.equals("Вторая Первая"));
    }

    @AfterEach
    void ConnectionsClose() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    @AfterAll
    static void ServerClose() throws InterruptedException {
        try (Socket socket = new Socket("localhost", 8989);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
            socket.setSoTimeout(1000);
            System.out.println("sending killswitch");
            out.println(Constants.killSwitch);
            serverThread.join();
        } catch (SocketException e) {
            fail("Can't connect to server");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
