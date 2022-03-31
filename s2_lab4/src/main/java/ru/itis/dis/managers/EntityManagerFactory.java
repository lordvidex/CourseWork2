package ru.itis.dis.managers;

//import ru.itis.dis.DatabaseContext;
import ru.itis.dis.utils.DbWorker;
import ru.itis.dis.utils.SQLHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA
 * Date: 30.03.2022
 * Time: 11:02 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */


public class EntityManagerFactory {
    private CharSequence url;

    private SQLHelper helper;
//    private DatabaseContext context;

    public EntityManagerFactory(SQLHelper helper) {
        this.helper = helper;
    }
    private HashMap<Long, EntityManager> entityManagerMap = new HashMap<>();

    public EntityManager createEntityManager() {
        Long id = Thread.currentThread().getId();
        if (entityManagerMap.containsKey(id)) {
            return entityManagerMap.get(id);
        } else {
            EntityManager em = new EntityManagerImpl(
                    DbWorker.getConnection(), helper);
            entityManagerMap.put(id, em);
            return em;
        }
    }

    public void destroy() {
        try {
            Connection conn = DbWorker.getConnection();
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
