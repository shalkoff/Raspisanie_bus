package ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalk_off on 07.10.2016.
 */

public class JSONAvtobuses {
    @SerializedName("bus")
    @Expose
    public List<Bu> bus = new ArrayList<Bu>();
    public class Bu {

        @SerializedName("idAvto")
        @Expose
        public String idAvto;
        @SerializedName("nomerAvto")
        @Expose
        public String nomerAvto;
        @SerializedName("name1")
        @Expose
        public String name1;
        @SerializedName("name2")
        @Expose
        public String name2;
        @SerializedName("v_gorod")
        @Expose
        public String vGorod;
        @SerializedName("s_goroda")
        @Expose
        public String sGoroda;
        @SerializedName("color")
        @Expose
        public String color;
        @SerializedName("price")
        @Expose
        public String price;
        @SerializedName("detals")
        @Expose
        public String detals;
    }
}
