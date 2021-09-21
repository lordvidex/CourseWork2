package ru.itis.dis.client;

import ru.itis.dis.Models.Message;

import java.io.*;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA
 * Date: 15.09.2021
 * Time: 10:47 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Client {
    private static Socket clientSocket;
    // reader for console
    private static BufferedReader reader;
    // in and out for reading from and writing to socket
    private static BufferedWriter out;
    private static BufferedReader in;
    private static String username;
    private static String recipient;

    private static void write(String message) throws IOException {
        out.write(message+"\n");
        out.flush();
    }

    private static void close() {
        try {
            write(":q");
            in.close();
            out.close();
        } catch (IOException ignored) {}
    }
    public static void main(String[] args) {
        try {
            clientSocket = new Socket("127.0.0.1", 10000);

            reader = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // object that indicates whether client is still connected or not
            var stopper = new Object() {
                boolean running = true;
            };
            System.out.println("Enter your username:");
            username = reader.readLine().trim();
            write(username);
            // listen for replies from the server and print to the output console
            Thread listenThread = new Thread(() -> {
                String serverResponse;

                try {
                    while (stopper.running) {
                        serverResponse = in.readLine();
                        if (serverResponse == null) {
                            stopper.running = false;
                            break;
                        }
                        System.out.println(serverResponse);
                    }
                    System.out.println("Enter any key to continue...");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            listenThread.start();

            while (stopper.running) {
                while (recipient == null || recipient.trim().isEmpty()) {
                    System.out.println("Enter recipient's username:");
                    recipient = reader.readLine().trim();
                }
                String message = reader.readLine().trim();
                if (message.equals("/back")) {
                    recipient = null;
                } else if (message.equals("/status")) {
                    System.out.println("Your username is " + username +
                            "\nYou are currently writing messages to " + recipient);
                } else if (message.equals("/quit")) {
                    write(":q");
                } else {
                    write(new Message(username, recipient, message).encoded());

                }
            }

            // close the client connections


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
