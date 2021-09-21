package ru.itis.dis.server;

import ru.itis.dis.Models.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 20.09.2021
 * Time: 9:01 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public interface MessageWorker {

    // sends a message to the user or adds to the queue
    // if the user is offline
   void sendMessage(Message message);
    /**
     * attaches socket to specific user to receive
     * incoming messages
     */
    void attachOutputStream(String username, BufferedWriter clientWriter);

    // returns true if user is online
    boolean isUserOnline(String username);

    // retrieves all the messages for a user once he logs in
    List<Message> getMessages(String username);

    void close(String username);

}
