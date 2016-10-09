package ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by shalk_off on 08.10.2016.
 */

public class JSONVersion {
    @SerializedName("version")
    @Expose
    public String version;
}
