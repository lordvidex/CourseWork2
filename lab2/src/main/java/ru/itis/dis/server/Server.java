package ru.itis.dis.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {

    private static final ArrayList<MicroServer> microServers = new ArrayList<>();
    private static ServerSocket server; // серверсокет

    private static void close() throws IOException {
        for(MicroServer m: microServers) {
            if(!m.isClosed()) m.close();
        }
        server.close();
    }

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(10000); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен в порт 10000"); // хорошо бы серверу

                // an open thread that listens for server requests
                Thread quitThread = new Thread(() -> {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        while(true) {
                            String line = br.readLine();
                            if(line.equals("/quit")) {
                                close();
                                break;
                            } else {
                                System.out.println("Enter /quit to stop server");
                            }
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                quitThread.start();
                while(!server.isClosed()) {
                    //   объявить о своем запуске
                    try {
                        Socket clientSocket = server.accept();
                        MicroServer newClient = new MicroServer(clientSocket);
                        new Thread(newClient).start(); // начинает новый сервер

                        microServers.add(newClient); // сохранить в массив
                        System.out.println(newClient + " connected");
                    } catch (SocketException err){
                        break;
                    }
                }

            } finally {
                System.out.println("Сервер закрыт!");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}