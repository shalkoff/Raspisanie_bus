package ru.bus.raspisanie.shalk_off.raspisanie_bus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shalk_off on 14.10.2016.
 */

public class DialogOcenka extends DialogFragment implements OnClickListener {
    private final static String APP_TITLE = "Расписание";
    private final static String APP_PACKAGE_NAME = "ru.bus.raspisanie.shalk_off.raspisanie_bus";
    final String LOG_TAG = "myLogs";
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity())
                .setTitle(APP_TITLE).setPositiveButton("Оценить", this)
                .setNegativeButton("Напомнить позже", this)
                .setNeutralButton("Спасибо, не надо",this)
                .setMessage("Мы заметили, что вы часто пользуетесь нашим приложением, пожалуйста оцените его в Google Play.\nБольшое спасибо!")
                .setCancelable(true);
        return adb.create();
    }

    public void onClick(DialogInterface dialog, int which) {
        SharedPreferences prefs = getActivity().getSharedPreferences("apprater", 0);
        SharedPreferences.Editor editor = prefs.edit();
        int i = 0;
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse("market://details?id=" + APP_PACKAGE_NAME)));
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                break;
            case Dialog.BUTTON_NEGATIVE:
                //Toast.makeText(getActivity(), "gggg2222", Toast.LENGTH_SHORT).show();
                break;
            case Dialog.BUTTON_NEUTRAL:
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                break;
        }
        if (i > 0)
            Log.d(LOG_TAG, "Dialog 2: " + getResources().getString(i));
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 2: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 2: onCancel");
    }

}