package ru.itis.utils;

/**
 * Created by IntelliJ IDEA
 * Date: 09.03.2022
 * Time: 1:46 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Converter {
    public static String camelCaseToSnakeCase(String val) {
        String str = val+"$";
        StringBuilder builder = new StringBuilder(str.substring(0,1).toLowerCase());
        for (int i = 1; i < str.length()-1; i++) {
             if (Character.isUpperCase(str.charAt(i))) {
                 builder.append("_")
                         .append(str.substring(i, i + 1)
                                 .toLowerCase());
             } else {
                 builder.append(str.charAt(i));
             }
        }
        return builder.toString();
    }
}
