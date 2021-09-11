package ru.itis.dis.server;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * Created by IntelliJ IDEA
 * Date: 07.09.2021
 * Time: 1:14 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

/**
 * Threaded MicroServer that handles each client
 */
public class MicroServer implements Runnable {
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;

    public MicroServer(Socket socket) throws IOException {
        this.clientSocket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public boolean isClosed() {
        return clientSocket.isClosed();
    }

    private void write(String message) {
        out.write(message);
        out.flush();
    }
    public void close() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Failed to close server.MicroServer's client socket: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        write("Enter a number from 1-10\n");
        int guessNumber = new Random().nextInt(11);
        int tries = 3;
        try {
            while (tries > 0) {
                write("You have " + tries + " tries left!\n");
                String temp = in.readLine();
                try {
                    int userEntry = Integer.parseInt(temp.trim()); // ждём пока клиент что-нибудь нам напишет
                    if (userEntry == guessNumber) {
                        write("Success, you guessed right\n");
                        break;
                    }
                    tries--;
                } catch (NumberFormatException error){
                    write("Invalid input. Press [ENTER] to continue and Enter a number from 1-10\n");
                }

            }
        } catch (IOException ignored ) { }

        if (tries == 0) {
            write("You failed to guess right\n");
        }
        // close the socket
        try {
            clientSocket.close();
            System.out.println(clientSocket + " closed!");
        } catch (IOException e) {
            System.out.println("Line 75");
            e.printStackTrace();
        }

    }
}
