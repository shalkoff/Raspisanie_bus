package ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses;

/**
 * Created by shalk_off on 10.10.2016.
 */

public class Price {
    final String name;
    final String price;

    public Price(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
