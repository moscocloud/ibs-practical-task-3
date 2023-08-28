package ru.ibs.framework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropManager {
    private final Properties properties = new Properties();
    private static TestPropManager INSTANCE = null;

    private TestPropManager() {
        loadAppProperties();
        loadCustomProperties();
    }

    public static TestPropManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestPropManager();
        }
        return INSTANCE;
    }

    /**
     * Загрузка содержимого файла properties из папки resources.
     * Или можно задать файл через Maven командой -DpropFile=(имя файла)
     */
    private void loadAppProperties() {
        try {
            properties.load(new FileInputStream(new File("src/main/resources/" +
                    System.getProperty("propFile", "application") + ".properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Замена значений(системных) на значения которые содержаться в файле properties,
     * замена по ключам
     */
    private void loadCustomProperties() {
        properties.forEach((key, value) ->
                System.getProperties().forEach((customUserKey, customUserValue) -> {
                    if (key.toString().equals(customUserKey.toString()) &&
                            !value.toString().equals(customUserValue.toString())) {
                        properties.setProperty(key.toString(), customUserValue.toString());
                    }
                }));
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
