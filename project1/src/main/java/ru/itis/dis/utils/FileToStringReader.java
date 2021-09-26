package ru.itis.dis.utils;

import java.io.*;

/**
 * Created by IntelliJ IDEA
 * Date: 26.09.2021
 * Time: 10:36 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class FileToStringReader {
    private String filepath;
    private File file;

    FileToStringReader(File file) {
        this.file = file;
    }

    public FileToStringReader(String filepath) {
        this(new File(filepath));
    }

    public String readToString() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine())!=null) {
            sb.append(line).append("\r\n");
        }
        return sb.toString();
    }
}
