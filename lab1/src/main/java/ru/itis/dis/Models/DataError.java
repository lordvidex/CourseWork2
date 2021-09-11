package ru.itis.dis.Models;

/**
 * Created by IntelliJ IDEA
 * Date: 01.09.2021
 * Time: 11:16 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class DataError {
    public static int ERROR = 500;
    public static int INCORRECT_ARGS_NUMBER = 501;
    public static int FILE_NOT_FOUND = 502;

    public int getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    private final int error;
    private final String description;

    public DataError(int error, String description){
        this.error = error;
        this.description = description;
    }
}
