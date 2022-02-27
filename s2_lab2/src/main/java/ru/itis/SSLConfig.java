package ru.itis;

import ru.itis.dis.s2lab1.annotations.Component;

/**
 * Created by IntelliJ IDEA
 * Date: 26.02.2022
 * Time: 10:12 PM
 *
 * @author lordvidex
 * Name: Овамойо Олувадамилола Эванс
 * <p>
 * Desc:
 */

@Component
public class SSLConfig {

    private String directory = "/Users/lordvidex/Desktop";


    public SSLConfig() {
        initFromConfigFile();
    }

    private void initFromConfigFile() {
        // initialize
    }

    /**
     * updates the settings by reinitializing
     */
    public void update() {
        initFromConfigFile();
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
