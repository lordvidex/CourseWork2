package ru.itis.dis.server;

import ru.itis.dis.Models.Message;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 20.09.2021
 * Time: 8:57 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class ClientProcess implements Runnable {
    private MessageWorker messageWorker;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final Socket clientSocket;
    private String username;
    private BufferedWriter recipient;

    ClientProcess(Socket clientSocket, MessageWorker messageWorker) throws IOException {
        this.messageWorker = messageWorker;
        this.clientSocket = clientSocket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            username = in.readLine();
            List<Message> pending = messageWorker.getMessages(username);
            if (pending != null) {
                write("-------Unread messages---------");
                for (Message message : pending) {
                    write(message.toChat());
                }
            }
            messageWorker.attachOutputStream(username, out);

            while(true) {
                String input = in.readLine();
                if(input == null || input.trim().equals(":q")) {
                    in.close();
                    out.close();
                    clientSocket.close();
                    messageWorker.close(username);
                    break;
                } else {
                    Message message = Message.decoded(input);
                    if(message != null) {
                        messageWorker.sendMessage(message);
                        write(message.toChat());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void write(String message) throws IOException {
        write(out,message);
    }

    private void write(BufferedWriter writer, String message) throws IOException {
        writer.write(message+"\n");
        writer.flush();
    }
}
