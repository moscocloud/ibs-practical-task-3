package ru.ibs.framework.utils;

public enum Product {
    TOMATO("Помидор", "Овощ", false),
    MANGO("Манго", "Фрукт", true),
    CUCUMBER("Огурец","Овощ",false);

    private String name;
    private String type;
    private boolean exotic;

    Product(String name, String type, boolean exotic) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isExotic() {
        return exotic;
    }
}
