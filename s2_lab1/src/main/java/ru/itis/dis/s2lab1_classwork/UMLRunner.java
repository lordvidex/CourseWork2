package ru.itis.dis.s2lab1_classwork;

import ru.itis.dis.s2lab1.PathScan;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 09.02.2022
 * Time: 2:22 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class UMLRunner {
    public static String PATH_FOR_SCAN = "ru.itis.dis";

    public static void main(String[] args) {
        List<Class<?>> classList = PathScan.find(PATH_FOR_SCAN);
        for (Class<?> cl: classList) {
            System.out.print(cl.getSimpleName());
            for (Field field: cl.getDeclaredFields()) {
                System.out.print("\n\t-------> "+field.getName()+" ------> "+ field.getType().getSimpleName());
                for (Annotation annot: field.getAnnotations()) {
                    System.out.print("\n\t\t ------> "+annot.annotationType());
                }
            }
            System.out.println();
        }
    }
}
