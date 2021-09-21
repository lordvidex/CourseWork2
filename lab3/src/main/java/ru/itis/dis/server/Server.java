package ru.itis.dis.server;

import ru.itis.dis.Models.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class Server {
    private static ServerSocket server;
    private static Map<String, List<Message>> messageQueue;
    private static Map<String, BufferedWriter> userSockets;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(10000);
            messageQueue = new HashMap<>();
            userSockets = new HashMap<>();
            System.out.println("Server running on port 10000");

            while (!server.isClosed()) {
                // wait for user connections
                Socket clientSocket = server.accept();

                // create a new process
                ClientProcess process = new ClientProcess(clientSocket,
                        new MessageWorker() {
                            @Override
                            public void sendMessage(Message message) {
                                try {
                                    if (isUserOnline(message.receiver)) {
                                        BufferedWriter writer = userSockets.get(message.receiver);
                                        writer.write(message.toChat()+"\n");
                                        writer.flush();
                                    } else {
                                        addMessageToQueue(message.receiver, message);
                                    }
                                } catch (IOException e) {
                                    close(message.receiver);
                                    addMessageToQueue(message.receiver, message);
                                }
                            }

                            @Override
                            public void attachOutputStream(String username, BufferedWriter clientWriter) {
                                userSockets.put(username.trim(), clientWriter);
                            }

                            @Override
                            public boolean isUserOnline(String username) {
                                return userSockets.containsKey(username.trim());
                            }

                            @Override
                            public List<Message> getMessages(String username) {
                                List<Message> messages = messageQueue.get(username.trim());
                                messageQueue.remove(username.trim());
                                return messages;
                            }

                            @Override
                            public void close(String username) {
                                userSockets.remove(username.trim());
                            }
                        });
                new Thread(process).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addMessageToQueue(String recipient, Message message) {
        if (messageQueue.containsKey(recipient)) {
            messageQueue.get(recipient).add(message);
        }else {
            messageQueue.put(recipient, new ArrayList<>() {{ add(message); }});
        }
    }
}
