package ru.itis;

import ru.itis.annotations.*;
import ru.itis.dis.s2lab1.PathScan;
import ru.itis.repositories.DbRepository;
import ru.itis.utils.Converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * Date: 09.03.2022
 * Time: 1:30 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class DatabaseContext {
    private final HashMap<String, List<String>> entities;

    public DatabaseContext(Class<?> mainClass) {
        entities = new HashMap<>();

        try {
            /* scan the classes in this package for entities */
            scanEntities(mainClass.getPackage().getName());

            /* compare with db */
            compareWithDb();

        } catch (Exception e) {
            System.err.println("Error scanning classes");
        }
    }

    private void scanEntities(String packagePath) {
        List<Class<?>> clses = PathScan.find(packagePath);
        entities.clear();

        for (Class<?> cl : clses) {
            if (cl.getAnnotation(Entity.class) != null) {
                // class is an entity, scan its fields

                List<String> entityFields = new ArrayList<>();
                for (Field field : cl.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.getAnnotation(Column.class) != null
                            || field.getAnnotation(PrimaryColumn.class) != null
                            || field.getAnnotation(OneToMany.class) != null
                    ) {
                        String fieldName = Converter.camelCaseToSnakeCase(field.getName());
                        entityFields.add(fieldName);
                    }
                }
                String entityName = Converter.camelCaseToSnakeCase(cl.getSimpleName());
                entities.put(entityName, entityFields);
            }
        }
    }

    private void compareWithDb() {

        DbRepository dbRepo = new DbRepository();

        for (var e : entities.entrySet()) {
            if (dbRepo.containsEntity(e.getKey())) {
                printLine(e.getKey(), false, true);
                e.getValue().forEach(val -> printLine(val, true, dbRepo.containsField(val, e.getKey())));
            } else {
                printLine(e.getKey(), false, false);
                e.getValue().forEach(val -> printLine(val, true, false));
            }
        }
    }

    private void printLine(String text, boolean isField, boolean success) {
        String emoji = success ? "✅" : "❌";
        String space = isField ? "\t" : "";
        System.out.println(space + emoji + " " + text);
    }
}
