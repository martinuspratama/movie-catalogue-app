package com.rmalan.app.moviecataloguealpha.reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.rmalan.app.moviecataloguealpha.MainActivity;
import com.rmalan.app.moviecataloguealpha.R;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {

    private static final String NOTIFICATION_CHANNEL_ID = "channel_01";
    private static final int NOTIFICATION_ID = 100;

    public DailyReminder() {

    }

    public static PendingIntent getPendingInteng(Context context) {
        Intent intent = new Intent(context, DailyReminder.class);
        return PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        activatedReminder(context, context.getString(R.string.app_name), context.getString(R.string.message_daily_reminder));
    }

    public void setDailyReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, DailyReminder.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private void activatedReminder(Context context, String title, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, DailyReminder.NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        @SuppressWarnings("deprecation") NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_local_movies)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(DailyReminder.NOTIFICATION_ID, builder.build());
    }

    public void disabledReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPendingInteng(context));
    }

}
