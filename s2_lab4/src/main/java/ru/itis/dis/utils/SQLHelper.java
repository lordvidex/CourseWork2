package ru.itis.dis.utils;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.itis.dis.exceptions.NotAnEntityException;
import ru.itis.utils.Constants;
import ru.itis.utils.DbWorker;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by IntelliJ IDEA
 * Date: 30.03.2022
 * Time: 3:29 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Getter
public class SQLHelper {
    private Set<String> tables;
    private Map<String,Set<String>> fields;
    private Connection conn;

    public SQLHelper() {
        tables = new HashSet<>();
        fields = new HashMap<>();

        conn = DbWorker.getConnection();
        getTables();
    }

    private void getTables() {
        try {
            ResultSet set = conn.prepareStatement(Constants.tableQuery).executeQuery();
            while(set.next()) {
                tables.add(set.getString("table_name"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting tables");
        }
    }

    private void getFields(String tableName) {
        try{
            ResultSet set = conn.prepareStatement(String.format(Constants.fieldQueryTemplate, tableName)).executeQuery();
            Set<String> values = new HashSet<>();
            while(set.next()) {
                values.add(set.getString("attname"));
            }
            fields.put(tableName, values);
        } catch (SQLException e) {
            System.err.println("Error getting fields");
        }
    }

    /**
     * Checks if the table contains a particular entity
     * @param entityName name of the entity / class
     * @return <b>true</b> if this entity has been scanned `false` otherwise
     */
    public boolean containsEntity(String entityName) {
        return tables.contains(entityName);
    }


    /**
     * checks if a certain field is contained in registered entity
     * @param fieldName name of the field / column
     * @param entityName name of the table or entity
     * @return true if this field exists in the table, false otherwise
     */
    public boolean containsField(String fieldName, String entityName) {
        if (!fields.containsKey(entityName)) {
            getFields(entityName);
        }

        if (fields.containsKey(entityName)) {
            return fields.get(entityName).contains(fieldName);
        } else {
            return false;
        }
    }

    public String valuesForObject(Set<String> fields, Object entity) throws IllegalAccessException {
        ArrayList<String> values = new ArrayList<>();
        Set<Field> entityFields = new HashSet<>(List.of(entity.getClass().getDeclaredFields()));
        for(String key: fields) {
            for (Field f: entityFields) {
                f.setAccessible(true);
                if (Objects.equals(f.getAnnotation(Column.class).name(), key)) {
                    Object value = f.get(entity);
                    if (value.getClass().equals(String.class)) {
                        value = "'"+value+"'";
                    }
                    values.add(value.toString());
                    entityFields.remove(f);
                    break;
                }
            }
        }
        return String.join(",", values);
    }
//    public static String insert(Object obj, String tableName) {
//        System.out.println(obj.getClass().getField(""))
//    }
    // generate insert statement
    public String insertQuery(Object instance) throws NotAnEntityException, IllegalAccessException {
        String tableName = instance.getClass().getAnnotation(Table.class).name();
        if (tableName == null || !tables.contains(tableName)) {
            throw new NotAnEntityException();
        }

        getFields(tableName);
        var set = new HashSet<>(fields.get(tableName)) {{
            remove("id");
        }};
        return "INSERT INTO "
                + tableName
                + "("+ String.join(",",set) +")"
                + " VALUES (" +
                    valuesForObject(set, instance)
                +");";
    }
    // generate find statement
    public String findQuery(Class<?> clazz, String id) {
        return "SELECT * from "+
                clazz.getAnnotation(Table.class).name()
                + " WHERE id="
                +id+";";
    }

    public String removeQuery(Class<?> clazz, String id) {
        String tableName = clazz.getAnnotation(Table.class).name();
        return "DELETE FROM "+ tableName + " WHERE id=" + id + ";";
    }


    // generate delete statement

    // generate update statement

}
