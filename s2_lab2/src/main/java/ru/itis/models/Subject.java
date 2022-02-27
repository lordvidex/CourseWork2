package ru.itis.models;

/**
 * Created by IntelliJ IDEA
 * Date: 26.02.2022
 * Time: 10:17 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */
public class Subject {
    public String countryCode = "";
    public String state = "";
    public String city = "";
    public String organization = "";
    public String organizationUnit = "";
    public String name = "";
    public String emailAddress = "";
    public String certificateName = "";

    public Subject(String certificateName, String countryCode, String state, String city , String organization, String organizationUnit, String name, String emailAddress) {
        this(certificateName);
        this.countryCode = countryCode; this.state = state;
        this.city = city; this.organization = organization; this.organizationUnit = organizationUnit;
        this.name = name; this.emailAddress = emailAddress;
    }

    public String abbr() {
        return String.format("/C=%s/ST=%s/L=%s/O=%s/OU=%s/CN=%s/emailAddress=%s/",
                countryCode,
                state,
                city,
                organization,
                organizationUnit,
                name,
                emailAddress);
    }

    public Subject(String certificateName) {
        this.certificateName = certificateName;
    }


}
