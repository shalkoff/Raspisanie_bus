package ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses;

/**
 * Created by shalk_off on 09.10.2016.
 */

public class Avtobuses {
    final String nomerAvto;
    final String name1;
    final String name2;
    final String color;

    public Avtobuses(String nomerAvto, String name1, String name2, String color) {
        this.nomerAvto = nomerAvto;
        this.name1 = name1;
        this.name2 = name2;
        this.color = color;
    }

    public String getNomerAvto() {
        return nomerAvto;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getColor() {
        return color;
    }
}
