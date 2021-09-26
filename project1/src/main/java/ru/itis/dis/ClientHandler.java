package ru.itis.dis;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA
 * Date: 26.09.2021
 * Time: 10:30 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Context context;

    public ClientHandler(Socket socket, Context context) {
        this.clientSocket = socket;
        this.context = context;
    }

    @Override
    public void run() {

        // create a request from client inputStream
        try {
            HttpRequest req = new HttpRequest(clientSocket.getInputStream());
            // and create a response from client outputStream
            HttpResponse res = new HttpResponse(clientSocket.getOutputStream());

            // return a handler from request path
            context.getContext(req.getPath()).process(req, res);
            clientSocket.close();
            System.out.println(clientSocket + " closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
