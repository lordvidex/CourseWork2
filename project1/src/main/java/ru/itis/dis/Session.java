package ru.itis.dis;

import java.util.Objects;

/**
 * Created by IntelliJ IDEA
 * Date: 30.09.2021
 * Time: 2:57 AM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Session {
    public String firstName;
    public String lastName;

    Session(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return firstName.equals(session.firstName) && lastName.equals(session.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public static Session fromString(String urlEncoded) {
        // keys
        String key1 = "firstName", key2 = "lastName";
        // values
        String val1 = "",val2 ="";
        // pointers
        String currKey = "", currValue;

        StringBuilder currString = new StringBuilder();
        for(char c: (urlEncoded+"&").toCharArray()) {
            if(c == '=') {
                currKey = currString.toString();
                currString = new StringBuilder();
            } else if (c == '&') {
                currValue = currString.toString();
                currString = new StringBuilder();
                if(currKey.equals(key1)) {
                    val1 = currValue;
                } else if (currKey.equals(key2)) {
                    val2 = currValue;
                }
            } else {
                currString.append(c);
            }
        }
        return new Session(val1,val2);
    }
}
