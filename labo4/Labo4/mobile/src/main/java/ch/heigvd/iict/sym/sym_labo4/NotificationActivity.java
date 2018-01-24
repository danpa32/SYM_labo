package ch.heigvd.iict.sym.sym_labo4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ch.heigvd.iict.sym.wearcommon.Constants;

public class NotificationActivity extends AppCompatActivity {
    private Button pendingIntent = null;
    private Button actionButton = null;
    private Button wearableOnlyActions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        if(getIntent() != null) {
            onNewIntent(getIntent());
        }

        pendingIntent = findViewById(R.id.b_pending_notifs);
        actionButton = findViewById(R.id.b_action_notifs);
        wearableOnlyActions = findViewById(R.id.b_wearable_only_notifs);

        pendingIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pendingNotifications();
            }
        });

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionNotification();
            }
        });


        wearableOnlyActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wearableOnlyNotification();
            }
        });
    }

    private void pendingNotifications() {
        String id = "channel_1";
        String title = getString(R.string.notif_title);
        String text = getString(R.string.simple_notif_text);

        PendingIntent resultPendingIntent = createPendingIntent(0, text);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, id)
                .setSmallIcon(R.drawable.ic_lightbulb_on_black_18dp)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(resultPendingIntent);

        notify(builder.build());
    }

    private void actionNotification() {
        String id = "channel_2";

        String title = getString(R.string.notif_title);
        String text = getString(R.string.action_button_notif_text);

        PendingIntent resultPendingIntent = createPendingIntent(0, text);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.ic_lightbulb_on_black_18dp)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_alert_black_18dp,
                                getString(R.string.action_button_text),
                                resultPendingIntent);

        notify(builder.build());
    }

    private void wearableOnlyNotification() {
        String id = "channel_3";

        String title = getString(R.string.notif_title);
        String text = getString(R.string.wearable_only_notif_text);

        PendingIntent resultPendingIntent = createPendingIntent(0, text);

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender().addAction(new NotificationCompat.Action(R.drawable.ic_alert_white_18dp,
                        getString(R.string.action_button_text),
                        resultPendingIntent));

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.ic_lightbulb_on_black_18dp)
                        .setContentTitle(title)
                        .setContentText(text)
                        .extend(wearableExtender)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_alert_black_18dp,
                                getString(R.string.action_button_text),
                                resultPendingIntent);

        notify(builder.build());
    }

    private void notify(Notification notification) {
        int notifId = 001;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notifId, notification);
    }

    /*
     *  Method called by system when a new Intent is received
     *  Display a toast with a message if the Intent is generated by
     *  createPendingIntent method.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent == null) return;
        if(Constants.MY_PENDING_INTENT_ACTION.equals(intent.getAction()))
            Toast.makeText(this, "" + intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
    }

    /**
     * Method used to create a PendingIntent with the specified message
     * The intent will start a new activity Instance or bring to front an existing one.
     * See parentActivityName and launchMode options in Manifest
     * See https://developer.android.com/training/notify-user/navigation.html for TaskStackBuilder
     * @param requestCode The request code
     * @param message The message
     * @return The pending Intent
     */
    private PendingIntent createPendingIntent(int requestCode, String message) {
        Intent myIntent = new Intent(NotificationActivity.this, NotificationActivity.class);
        myIntent.setAction(Constants.MY_PENDING_INTENT_ACTION);
        myIntent.putExtra("msg", message);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(myIntent);

        return stackBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void CancelNotification(Context ctx, int notifyId) {
        NotificationManager mNM = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        assert mNM != null;
        mNM.cancel(notifyId);
    }

}
