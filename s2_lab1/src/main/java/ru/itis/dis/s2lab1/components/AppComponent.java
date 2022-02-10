package ru.itis.dis.s2lab1.components;

import ru.itis.dis.s2lab1.annotations.Component;

/**
 * Created by IntelliJ IDEA
 * Date: 10.02.2022
 * Time: 2:55 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Component
public class AppComponent {

    AppComponent(){
        init();
    }

    public void greetUser(String userName) {
        System.out.println("Hello "+userName+"! from AppComponent");
    }

    public void init() {
        System.out.println("This is init() method from AppComponent");
    }
}
