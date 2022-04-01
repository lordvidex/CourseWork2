package ru.itis.dis.managers;

import ru.itis.dis.exceptions.NotAnEntityException;
import ru.itis.dis.utils.SQLHelper;
import ru.itis.dis.utils.SQLRunner;


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
    private SQLHelper helper;


    public EntityManagerImpl(SQLHelper helper) {
        this.helper = helper;
    }
    @Override
    public <T> T persist(Object var1) throws Exception {
        isEntity(var1); // check if it's an entity
        Long id = SQLRunner.insert(helper.insertQuery(var1));
        return (T) find(var1.getClass() , id);
    }

    @Override
    public <T> T merge(T var1) throws Exception {
        isEntity(var1);
        Long id = SQLRunner.update(helper.updateQuery(var1));
        return (T) find(var1.getClass(), id);
    }

    @Override
    public <T> void remove(Class<T> var1, Object var2) throws Exception {
        SQLRunner.remove(helper.removeQuery(var1,var2.toString()));
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
