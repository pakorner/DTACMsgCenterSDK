package com.dtacmsgcentersdk.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.dtacmsgcentersdk.MainActivity;
import com.dtacmsgcentersdk.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by Llvve on 3/21/2017 AD.
 */

public class NotificationHandler {

    private static NotificationHandler instance = null;

    private NotificationHandler() {
        // Exists only to defeat instantiation.
    }

    public static NotificationHandler getInstance() {
        if(instance == null) {
            instance = new NotificationHandler();
        }
        return instance;
    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param data FCM data received.
     */
    public void sendNotification(Context context, Map<String, String> data) {
        ArrayList<Intent> intentArrayList = new ArrayList<>();
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentArrayList.add(mainIntent);

        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0 /* Request code */, intentArrayList.toArray(new Intent[]{}),
                PendingIntent.FLAG_UPDATE_CURRENT);

        String title = (data.get("title") != null) ? data.get("title") : "";
        String message =  (data.get("message") != null) ? data.get("message") : "";
        String content_type =  (data.get("content_type") != null) ? data.get("content_type") : "";

        RemoteViews remoteViewsBig = null;
        RemoteViews remoteViewsSmall = null;
        if (content_type.equals("text-image")) {
            remoteViewsBig = new RemoteViews(context.getPackageName(), R.layout.notice_text_image_big);

            FutureTarget<Bitmap> futureTarget = Glide.with(context)
                    .load("http://www.dtac.co.th/cms-storage/4g/new-4g/x_STAR-web-AUM_banner_page_m_480x400-th.jpg.pagespeed.ic.gINQPk7YN2.jpg")
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            try {
                remoteViewsBig.setImageViewBitmap(R.id.iv_img, futureTarget.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            Glide.clear(futureTarget);

            remoteViewsBig.setTextViewText(R.id.tv_title, title);
            remoteViewsBig.setTextViewText(R.id.tv_message, message);

            remoteViewsSmall = new RemoteViews(context.getPackageName(), R.layout.notice_text_image_small);
//            remoteViewsSmall.setImageViewResource(R.id.iv_img, R.mipmap.ic_launcher_alpha);
            remoteViewsSmall.setTextViewText(R.id.tv_title, title);
            remoteViewsSmall.setTextViewText(R.id.tv_message, message);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(getNotificationIcon())
                    .setColor(context.getColor(R.color.colorAccent))
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setVibrate(new long[] {1000, 1000})
                    .setContentIntent(pendingIntent);
        }else {
            notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(getNotificationIcon())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setVibrate(new long[] {1000, 1000})
                    .setContentIntent(pendingIntent);
        }

        if (remoteViewsBig != null) {
            notificationBuilder.setCustomContentView(remoteViewsSmall);
            notificationBuilder.setCustomBigContentView(remoteViewsBig);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Random rand = new Random();
        int randomId = rand.nextInt((Integer.MAX_VALUE));
        notificationManager.notify(randomId /* ID of notification */, notificationBuilder.build());

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("push_notification");
        broadcastIntent.putExtra("category", (data.containsKey("category")) ? data.get("category") : "");
        broadcastIntent.putExtra("data_ref", (data.containsKey("data_ref")) ? data.get("data_ref") : "");

        context.sendBroadcast(broadcastIntent);
    }

    private static int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.mipmap.ic_launcher_alpha : R.mipmap.ic_launcher;
    }

}
