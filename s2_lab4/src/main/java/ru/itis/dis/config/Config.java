package ru.itis.dis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.itis.dis.Application;
import ru.itis.dis.managers.EntityManagerFactory;
import ru.itis.dis.utils.SQLHelper;

/**
 * Created by IntelliJ IDEA
 * Date: 30.03.2022
 * Time: 1:22 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
@Configuration
@ComponentScan({"ru.itis.dis"})
public class Config {

    @Bean(destroyMethod = "destroy")
    @Scope("singleton")
    public EntityManagerFactory getEntityManagerFactory(SQLHelper helper) {
        return new EntityManagerFactory(helper);
    }

    @Bean
    @Scope("singleton")
    public SQLHelper getSQLHelper() {
        return new SQLHelper();
    }
}
