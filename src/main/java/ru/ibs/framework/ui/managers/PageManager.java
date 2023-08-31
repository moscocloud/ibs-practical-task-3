package ru.ibs.framework.ui.managers;

import ru.ibs.framework.ui.pages.BasePage;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class PageManager {
    private static PageManager INSTANCE = null;
    private static Map<String, Object> mapPages = new HashMap<>();

    public PageManager() {
    }

    public static PageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public <T extends BasePage> T getPage(Class<T> page) {
        if (mapPages.isEmpty() || mapPages.get(page.getName()) == null) {
            try {
                mapPages.put(page.getName(), page.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return (T) mapPages.get(page.getName());
    }

    public void clearPages() {
        mapPages.clear();
    }
}
