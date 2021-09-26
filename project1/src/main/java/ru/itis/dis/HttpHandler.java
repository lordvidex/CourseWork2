package ru.itis.dis;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA
 * Date: 26.09.2021
 * Time: 1:15 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public interface HttpHandler {
    void process(HttpRequest req, HttpResponse res) throws IOException;
}
