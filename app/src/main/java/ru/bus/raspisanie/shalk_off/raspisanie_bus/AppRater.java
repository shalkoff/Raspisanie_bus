package ru.bus.raspisanie.shalk_off.raspisanie_bus;

/**
 * Created by shalk_off on 14.10.2016.
 */

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys.MainActivity;


public class AppRater {
    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;

    public static void app_launched(Context context) {
        DialogFragment dlg2 = new DialogOcenka();
        MainActivity activity = (MainActivity)context;
        SharedPreferences prefs = context.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }
        SharedPreferences.Editor editor = prefs.edit();
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch
                    + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                dlg2.show(activity.getFragmentManager(), "dlg2");
            }
        }
        editor.commit();
    }
}
