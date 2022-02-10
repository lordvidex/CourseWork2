package ru.itis.dis.s2lab1_classwork.components;

import ru.itis.dis.s2lab1_classwork.annotations.Person;

/**
 * Created by IntelliJ IDEA
 * Date: 09.02.2022
 * Time: 2:21 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Person
public class GrandParent {
    @Person
    public Parent parent;
    @Person
    public Child child;
}
