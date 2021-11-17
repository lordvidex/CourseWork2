package ru.itis.dis.lab9.Listeners;

import ru.itis.dis.lab9.DbWorker;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by IntelliJ IDEA
 * Date: 04.11.2021
 * Time: 2:59 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@WebListener
public class AppListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("App Listener init");
        DbWorker.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
