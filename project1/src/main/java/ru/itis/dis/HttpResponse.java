package ru.itis.dis;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA
 * Date: 26.09.2021
 * Time: 1:16 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class HttpResponse {
    // sets a feed back line and a new line
    private static final String NEW_LINE = "\r\n";

    // contains all the headers except the cookies
    private final Map<String, String> header;

    // contains the body of a text return contentType.
    private String body;

    // header title on the first line of the header
    private String title = "HTTP/1.1 200 OK";

    // output stream of client to write to
    private final OutputStream os;

    // cookies to be sent to client
    private final Map<String,String> cookies;

    HttpResponse(OutputStream os) {
        this.os = os;
        this.cookies = new HashMap<>();
        this.header = new HashMap<>();
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCookie(String key, String value) {
        cookies.put(key,value);
    }

    public void setCookie(String key, String value, int expiryInSeconds) {
        setCookie(key,value+"; Max-Age="+expiryInSeconds);
    }

    public void setHeader(String key, String value) {
        header.put(key, value);
    }

    public void setHeaders(Map<String, String> headers) {
        header.putAll(headers);
    }

    public void setTitle(String httpVersion, String statusCode, String statusMessage) {
        title = httpVersion+" "+statusCode+" "+statusMessage;
    }

    public void send() throws IOException {
        addServerHeader();
        addContentLengthHeader();
        StringBuilder response = new StringBuilder();
        response.append(title).append(NEW_LINE);
        header.forEach((k,v) -> response.append(k).append(": ").append(v).append(NEW_LINE));
        cookies.forEach((k,v) -> response.append("Set-Cookie: ")
                .append(k).append("=").append(v).append("; HttpOnly").append(NEW_LINE));
        // header has finished
        response.append(NEW_LINE);

        // body
        if(body != null) {
            response.append(body);
        }

        // write, flush, close
        os.write(response.toString().getBytes());
        os.flush();
    }

    private void addContentLengthHeader() {
        if(body!=null) {
            setHeader("Content-Length", ""+body.getBytes(StandardCharsets.UTF_8).length);
        }
    }

    private void addServerHeader() {
        setHeader("Date", new Date().toString());
        setHeader("Server", "Java Test Server");
    }

}
