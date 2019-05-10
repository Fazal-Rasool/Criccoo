package com.adaxiom.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.adaxiom.criccoo.MainActivity;
import com.adaxiom.criccoo.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pixplicity.easyprefs.library.Prefs;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;
import static com.adaxiom.utils.Constants.PREF_BLOCK_ID;
import static com.adaxiom.utils.Constants.PREF_FCM_TOKEN;
import static com.adaxiom.utils.Constants.PREF_INNING_ID;
import static com.adaxiom.utils.Constants.PREF_MATCH_ID;

public class FirebaseNotificationService extends FirebaseMessagingService {

    int typeId = 0;
    public static final String CHANNEL_ID = "criccoo_01";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("NEW_TOKEN_SERVICE", token);
        Prefs.putString(PREF_FCM_TOKEN, token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String body = "";
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject object = new JSONObject(remoteMessage.getData());
                Log.e(TAG, "JSON_OBJECT: " + object.toString());
                body = object.optString("message");
                parseNotificationString(body);
                Log.e(TAG, "TypeId: " + body);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        notificationFunction("Criccoo", body);
    }

    private void notificationFunction(String title, String body) {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.notification_icon)
                .setTicker("Hearty365")
                .setContentTitle(title)
                .setContentText(body)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());


//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1/*Request code*/, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        //Set sound of notification
//        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        long[] v = {1000, 1000};
//        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.notification_icon)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setAutoCancel(false)
//                .setVibrate(v)
//                .setSound(notificationSound)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1 /*ID of notification*/, notifiBuilder.build());
    }

    public void parseNotificationString(String body) {

        int block = 0;

        String[] notificationArray = body.split(",");
        String matchId = notificationArray[0];
        String inn = notificationArray[1];
        String blockId = notificationArray[2];

        if (!blockId.equalsIgnoreCase(""))
            block = Integer.parseInt(blockId);

        Prefs.putString(PREF_MATCH_ID, matchId);
        Prefs.putString(PREF_INNING_ID, inn);
        Prefs.putInt(PREF_BLOCK_ID, block);

    }

}
