package ru.itis.dis.managers;

import ru.itis.dis.exceptions.NotAnEntityException;
import ru.itis.dis.utils.SQLHelper;


import javax.persistence.Entity;
import java.sql.Connection;

/**
 * Created by IntelliJ IDEA
 * Date: 30.03.2022
 * Time: 1:24 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

public class EntityManagerImpl implements EntityManager {
    private Connection connection;
    private SQLHelper helper;


    public EntityManagerImpl(Connection connection, SQLHelper helper) {
        this.connection = connection;
        this.helper = helper;
    }
    @Override
    public void persist(Object var1) throws Exception {
        isEntity(var1); // check if it's an entity
//        connection.createStatement().execute
    }

    @Override
    public <T> T merge(T var1) throws Exception {
        return null;
    }

    @Override
    public void remove(Object var1) throws Exception {

    }

    @Override
    public <T> T find(Class<T> var1, Object var2) throws Exception {
        return SQLRunner.find(helper.findQuery(var1,var2.toString()),var1);
    }

    public void isEntity(Object obj) throws NotAnEntityException {
        if (!obj.getClass().isAnnotationPresent(Entity.class)) {
            throw new NotAnEntityException();
        }
    }
}
