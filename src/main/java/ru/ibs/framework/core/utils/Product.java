package ru.ibs.framework.core.utils;

public enum Product {
    TOMATO("Помидор", "Овощ", false, "VEGETABLE"),
    MANGO("Манго", "Фрукт", true, "FRUIT"),
    CUCUMBER("Огурец","Овощ",false, "VEGETABLE");

    private String name;
    private String type;
    private boolean exotic;
    private String typeForAPI;

    Product(String name, String type, boolean exotic, String typeForAPI) {
        this.name = name;
        this.type = type;
        this.exotic = exotic;
        this.typeForAPI = typeForAPI;
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

    public String getTypeForAPI() {return typeForAPI;}

    @Override
    public String toString() {
        return "Product {" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", exotic=" + exotic +
                '}';
    }

    public String postBody() {
        return String.format("{\"name\":\"%s\", \"type\":\"%s\",\"exotic\":%s}",
                name, typeForAPI, exotic);
    }
}
