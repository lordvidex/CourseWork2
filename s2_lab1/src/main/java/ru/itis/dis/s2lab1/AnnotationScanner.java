package ru.itis.dis.s2lab1;

/**
 * Created by IntelliJ IDEA
 * Date: 09.02.2022
 * Time: 12:48 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

import ru.itis.dis.s2lab1.classes.TestClass;
import ru.itis.dis.s2lab1.components.TestComponent;

/**
 * IoC Inversion of Control
 */
public class AnnotationScanner {

    public static void main(String[] args) {
        Context appContext = new Context(AnnotationScanner.class);

        System.out.println("---------------------------------");

        // Test 1
        TestClass testClass = appContext.get(TestClass.class);

        /* testClass functions should work */
        testClass.print();
        testClass.greet("Evans");

        // Test 2
        /* Instance from appContext should be equal to TestClass's field */
        TestComponent t1 = appContext.get(TestComponent.class);
        TestComponent t2 = testClass.testComponent;
        System.out.println(t1 + " is"
                + (t1.equals(t2) ? " " : " NOT ")
                + "equal to " + t2
        );

    }
}
