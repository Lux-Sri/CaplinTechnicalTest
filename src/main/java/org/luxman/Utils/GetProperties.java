package org.luxman.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {

    private final String browser;

    public GetProperties() {
        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader("src/main/resources/Config.properties");
            properties.load(fileReader);
            browser = properties.getProperty("browser");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBrowser() {
        return browser;
    }
}
