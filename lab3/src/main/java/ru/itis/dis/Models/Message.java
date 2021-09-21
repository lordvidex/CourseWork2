package ru.itis.dis.Models;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by IntelliJ IDEA
 * Date: 20.09.2021
 * Time: 10:07 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Message {
    public String sender;
    public String receiver;
    public String message;

    public Message(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public String toChat() {
        return sender+"->"+receiver+": "+message;
    }

    public String encoded() {
        byte[] payload1 = sender.getBytes(StandardCharsets.UTF_8);
        byte[] payload2 = receiver.getBytes(StandardCharsets.UTF_8);
        byte[] payload3 = message.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(payload1)
                +"."
                +Base64.getEncoder().encodeToString(payload2)
                +"."
                +Base64.getEncoder().encodeToString(payload3);
    }

    public static Message decoded(String payload) {
        String[] payloads = payload.split("\\.");
        try {
            String sender = new String(Base64.getDecoder().decode(payloads[0]));
            String receiver = new String(Base64.getDecoder().decode(payloads[1]));
            String message = new String(Base64.getDecoder().decode(payloads[2]));
            return new Message(sender, receiver, message);
        } catch (Exception e) {
            return null;
        }
    }
}
