package ru.itis.dis.managers;

import ru.itis.dis.DbWorker;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA
 * Date: 31.03.2022
 * Time: 1:15 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class SQLRunner {
    static void insert(String sql) throws SQLException {
        var result = Objects.requireNonNull(DbWorker.getConnection())
                .prepareStatement(sql,0).executeQuery();
        System.out.println(result);
    }

    static <T> T find(String sql, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException {
        var result = Objects.requireNonNull(DbWorker.getConnection())
                .prepareStatement(sql).executeQuery();
        T obj = clazz.newInstance();
        while(result.next()) {
            for (Field f: obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if(f.isAnnotationPresent(Column.class)) {
                    String name = f.getAnnotation(Column.class).name();
                    f.set(obj,parseToType(f.getType(),result.getString(name)));
                }
            }
        }
        return obj;
    }

    static Object parseToType(Class<?> type, String value) {
        if(type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        }
        return value;
    }
}
