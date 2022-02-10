package ru.itis.dis.s2lab1.classes;

import ru.itis.dis.s2lab1.annotations.Component;
import ru.itis.dis.s2lab1.annotations.Inject;
import ru.itis.dis.s2lab1.components.AppComponent;
import ru.itis.dis.s2lab1.components.TestComponent;

/**
 * Created by IntelliJ IDEA
 * Date: 09.02.2022
 * Time: 12:49 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

public class TestClass {
    @Inject
    public TestComponent testComponent;
    @Inject
    private AppComponent appComponent;

    public void print() {
        System.out.println(testComponent.getComponentInfo());
    }

    public void greet(String user) {
        appComponent.greetUser(user);
    }
}
