package ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses;

/**
 * Created by shalk_off on 03.10.2016.
 */
public class Info {
    final String idAvto;
    final String nomerAvto;
    final String name1;
    final String name2;
    final String vGorod;
    final String sGoroda;
    final String color;
    final String price;
    final String detals;

    public Info(String idAvto, String nomerAvto, String name1, String name2, String vGorod, String sGoroda, String color, String price, String detals) {
        this.idAvto = idAvto;
        this.nomerAvto = nomerAvto;
        this.name1 = name1;
        this.name2 = name2;
        this.vGorod = vGorod;
        this.sGoroda = sGoroda;
        this.price = price;
        this.color = color;
        this.detals = detals;
    }

    public String getIdAvto() {
        return idAvto;
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

    public String getvGorod() {
        return vGorod;
    }

    public String getsGoroda() {
        return sGoroda;
    }

    public String getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getDetals() {
        return detals;
    }
}
