package ru.ibs.framework.core.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductData<T>{
    String name;
    String type;
    T exotic;

    public ProductData() {}

    public ProductData(String name, String type, T exotic) {
        this.name = name;
        this.type = type;
        this.exotic = (T) exotic;
    }

    @Override
    public String toString() {
        return  '\n' + "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", exotic=" + exotic;
    }
}
