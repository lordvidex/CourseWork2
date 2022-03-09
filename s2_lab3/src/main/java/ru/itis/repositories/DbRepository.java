package ru.itis.repositories;

import ru.itis.utils.Constants;
import ru.itis.utils.DbWorker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA
 * Date: 09.03.2022
 * Time: 3:23 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class DbRepository {
    private Connection conn;
    private Set<String> tables;
    private Map<String,Set<String>> fields;
    public DbRepository() {
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

    public boolean containsEntity(String entityName) {
        return tables.contains(entityName);
    }
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
}
