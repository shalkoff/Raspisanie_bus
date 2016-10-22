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
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys.MainActivity;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.GetDiveceName;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest.JSONAddToDB;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private SharedPreferences prefs = null; // Чек уведомления об обновлении
    private static final String TAG = "MyFirebaseMsgService";
    private String md5(String original){
        StringBuffer sb=null;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());
        byte[] digest = md.digest();
        sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if(remoteMessage.getData().get("message").equals("token"))
        {
            String tokenKlient = FirebaseInstanceId.getInstance().getToken();
            String md5TokenServer = remoteMessage.getData().get("body");
            String success = prefs.getString("success", "");
            String md5TokenKlient = md5(tokenKlient);
            if (md5TokenKlient.equals(md5TokenServer) && success.equals("1")) {
                // Делаем запрос на добавление в Бд
                // Добавляем в БД:модель телефона, токен, андройдАйДи
                GetDiveceName getDiveceName = new GetDiveceName();
                String nameDivece = getDiveceName.getDeviceName();
                String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                addUserMyBD(nameDivece,md5TokenKlient, tokenKlient, androidId);

            }
        } else {
            //Чек обновления
            if (prefs.getBoolean("checkupdate", true)) {
                sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"));
            }
        }



        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload1111111111: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void addUserMyBD(String nameDivece, String md5TokenKlient, String tokenKlient, String androidId) {
        // Запрос к БД на добавление пользователя
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://serverman4ik.myarena.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       APIService service = retrofit.create(APIService.class);
        Call<String> call = service.setUser(nameDivece,tokenKlient,androidId,md5TokenKlient);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body().toString().equals("success")){
                        Log.d(TAG, "Пользователь добавлен!");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

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