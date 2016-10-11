package ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses;

/**
 * Created by shalk_off on 09.10.2016.
 */

public class Raspisanie {
    final String time_otp;
    final String time;
    final int image;

    public Raspisanie(String _time_otp, String _time, int _image) {
        time_otp = _time_otp;
        time = _time;
        image = _image;
    }

    public String getTime_otp() {
        return time_otp;
    }

    public String getTime() {
        return time;
    }

    public int getImage() {
        return image;
    }
}
