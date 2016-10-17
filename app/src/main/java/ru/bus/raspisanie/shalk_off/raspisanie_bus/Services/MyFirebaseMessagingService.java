package ru.bus.raspisanie.shalk_off.raspisanie_bus.Services;

/**
 * Created by shalk_off on 15.10.2016.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys.MainActivity;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private SharedPreferences prefs = null; // Чек уведомления об обновлении
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Чек обновления
        if (prefs.getBoolean("checkupdate", true)) {
         //   sendNotification(remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String messageTitle, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.iconka);
        NotificationCompat.Builder notificationBuilder = null;
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_avtobus)
                .setLargeIcon(largeIcon)
                .setContentTitle(messageTitle)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setLights(Color.BLUE, 100, 100)
                .setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT > 20){
            notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody));
            notificationBuilder.setVibrate(new long[] { 100, 100, 100, 100 });
        }
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}