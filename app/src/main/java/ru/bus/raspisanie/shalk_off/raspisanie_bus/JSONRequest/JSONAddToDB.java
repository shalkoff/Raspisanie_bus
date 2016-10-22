package ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shalk_off on 22.10.2016.
 */

public class JSONAddToDB {

    @SerializedName("multicast_id")
    @Expose
    public String multicastId;
    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("failure")
    @Expose
    public String failure;
    @SerializedName("canonical_ids")
    @Expose
    public String canonicalIds;
    @SerializedName("results")
    @Expose
    public List<Result> results = new ArrayList<Result>();

    public class Result {

        @SerializedName("error")
        @Expose
        public String error;

    }
}
