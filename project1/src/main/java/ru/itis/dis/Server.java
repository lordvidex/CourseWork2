package ru.itis.dis;

import ru.itis.dis.handlers.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA
 * Date: 26.09.2021
 * Time: 12:58 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Server {
    public static void main(String[] args) {

        // create a context and map routes to handlers
        Context context = new Context();
        context.createContext("/", new RootHandler());
        context.createContext("/login", new LoginHandler());
        context.createContext("/app", new AppHandler());
        context.createNotFoundContext(new ErrorHandler());

        try {
            // create a new server socket
            int port = 80;
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server running on port "+port);

            // listen for client connections
            while(true) {
                Socket clientSocket = server.accept();
                System.out.println(clientSocket + " connected");
                new Thread(new ClientHandler(clientSocket,context)).start();
            }

        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
