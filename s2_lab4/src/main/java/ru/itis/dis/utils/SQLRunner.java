package ru.itis.dis.utils;

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
    public static Long insert(String sql) throws SQLException {
        var statement = Objects.requireNonNull(DbWorker.getConnection())
                .prepareStatement(sql, new String[]{"id"});
//        var result = statement.executeQuery();
        int affectedRows = statement.executeUpdate();
        if (affectedRows > 0) {
            var result = statement.getGeneratedKeys();
            result.next();
            return result.getLong("id");
        }
        return null;
    }

    public static <T> T find(String sql, Class<T> clazz) throws SQLException, InstantiationException, IllegalAccessException {
        var result = Objects.requireNonNull(DbWorker.getConnection())
                .prepareStatement(sql).executeQuery();
        T obj = null;
        while(result.next()) {
            obj = clazz.newInstance();
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

    public static void remove(String sql) throws SQLException {
        DbWorker.getConnection().prepareStatement(sql, new String[]{"id"}).execute();
    }

    static Object parseToType(Class<?> type, String value) {
        if(type.equals(int.class) || type.equals(Integer.class)) {
            return Integer.parseInt(value);
        }
        return value;
    }
}
