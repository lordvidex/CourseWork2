package ru.itis.dis.s2lab1;

import ru.itis.dis.s2lab1.annotations.Component;
import ru.itis.dis.s2lab1.annotations.Inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public class Context {
    private final HashMap<Class<?>, Object> components;
    private final HashMap<Class<?>, Object> classes;

    public <T> T get(Class<T> type) {
        if (components.containsKey(type)) {
            return (T) components.get(type);
        }
        return (T) classes.get(type);
    }

    Context(Class<?> mainClass) {
        components = new HashMap<>();
        classes = new HashMap<>();
        try {
            initClasses(mainClass.getPackage().getName());
        } catch(Exception e) {
            System.err.println("Error instantiating classes");
        }
    }

    private void initClasses(String packagePath) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Class<?>> clses = PathScan.find(packagePath);

        for(Class<?> cl: clses) {
            for(Annotation annotation: cl.getAnnotations()) {
                if (annotation.annotationType().equals(Component.class)) {
                    Constructor<?> c = cl.getDeclaredConstructor();
                    c.setAccessible(true);
                    this.components.put(cl, c.newInstance());
                }
            }
        }

        for (Class<?> cl: clses) {
            for (Field field: cl.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.getAnnotation(Inject.class) != null) {
                    classes.putIfAbsent(cl,cl.getDeclaredConstructor().newInstance());
                    field.set(classes.get(cl),components.get(field.getType()));
                }
            }
        }
    }
}