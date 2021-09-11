package ru.itis.dis.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by IntelliJ IDEA
 * Date: 07.09.2021
 * Time: 11:17 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

public class Client {

    private static Socket clientSocket; //сокет для общения
    private static BufferedReader reader; // нам нужен ридер читающий с консоли, иначе как
    // мы узнаем что хочет сказать клиент?
    private static BufferedReader in; // поток чтения из сокета
    private static PrintWriter out; // поток записи в сокет

    private static void write(String message) {
        out.write(message);
        out.flush();
    }

    public static void main(String[] args) {
        try {
            clientSocket = new Socket("127.0.0.1", 10000); // этой строкой мы запрашиваем

            //  у сервера доступ на соединение
            reader = new BufferedReader(new InputStreamReader(System.in));
            // читать соообщения с сервера
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // писать туда же
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            var stopper = new Object() {
                boolean running = true;
            };

            // слушать от сервера синхро..
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
                        if (serverResponse.contains("Success")
                                || serverResponse.contains("failed")) {
                            stopper.running = false;
                            break;
                        }
                    }
                    System.out.println("Enter any key to continue...");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            listenThread.start();

            // написать серверу
            while (stopper.running) {
                String answer = reader.readLine() + "\n";
                write(answer);
            }

        }catch (SocketException e){
            System.err.println("Failed to connect to server...");
        }
        catch (IOException e) {
            System.err.println("An IOException occurred");
        } finally { // в любом случае необходимо закрыть сокет и потоки
            System.out.println("Клиент был закрыт...");
        }
    }
}