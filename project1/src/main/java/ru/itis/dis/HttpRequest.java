package ru.itis.dis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class HttpRequest {
    private InputStream is;

    private Map<String, String> headers;

    private String method;

    private String path;

    private String httpVersion;
    
    private String body;

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public String getHttpVersion() {
        return httpVersion;
    }


    HttpRequest(InputStream is) throws IOException {
        this.is = is;
        headers = new HashMap<>();
        parseValues(is);
    }

    private void parseValues(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);

        // gather characters until a line is formed
        StringBuilder lineBuilder = new StringBuilder();

        // read first line
        int token = isr.read();
        char tokenCharacter = (char) token;
        while (tokenCharacter != '\n') {
            if (tokenCharacter == '\r') {
                // gather the data and set values
                String firstLine = lineBuilder.toString();
                setMethodAndPath(firstLine);
                lineBuilder = new StringBuilder();
            } else {
                lineBuilder.append(tokenCharacter);
            }
            // update with new character
            token = isr.read();
            tokenCharacter = (char) token;
        }

        // read headers
        boolean carriage = false;
        int lineCharCount = 0;
        token = isr.read();
        tokenCharacter = (char) token;

        while (true) {
            if (tokenCharacter != '\r' && tokenCharacter != '\n') {
                lineCharCount++;
                lineBuilder.append(tokenCharacter);
            } else if (tokenCharacter == '\r') {
                carriage = true;
            } else {
                if (carriage && lineCharCount == 0) {
                    // an empty line
                    break;
                } else if (carriage && lineCharCount > 0) {
                    carriage = false;
                    lineCharCount = 0;
                    String line = lineBuilder.toString();
                    setHeader(line);
                    lineBuilder = new StringBuilder();
                }
            }
            token = isr.read();
            tokenCharacter = (char) token;
        }

        // read body
        if(headers.containsKey("Content-Length")) {
            int n;
            while ((n = isr.read()) != -1) {
                lineBuilder.append((char) n);
            }
            if (lineBuilder.length() != 0) body = lineBuilder.toString();
        }
    }

    private void setMethodAndPath(String firstLine) {
        String[] arr = firstLine.split(" ");
        method = arr[0];
        path = arr[1];
        httpVersion = arr[2];
    }

    private void setHeader(String line) {
        String[] arr = line.split(":");
        headers.put(arr[0].trim(), arr[1].trim());
    }

    public String getBody() {
        return body;
    }
}
