package ch.heigvd.iict.sym.sym_labo4;

import android.app.Notification;
import android.app.PendingIntent;
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

/**
 * Activity responsible for displaying the action buttons, providing to the user the possibility to
 * display notifications.
 * Three types of notifications are available :
 * - Pending notification - Displayed on both phone and wearable, shows a text notification
 * - Action notification - Displayed on both phone and wearable, shows a text notification with an
 * action area (when the user touches the area, an action is performed. Here, a toast is displayed)
 * - Wearable only notification - As the action notification but displayed on wearable only.
 *
 * @author Christopher MEIER, Guillaume MILANI, Daniel PALUMBO
 */
public class NotificationActivity extends AppCompatActivity {
    private Button pendingIntent = null;
    private Button actionButton = null;
    private Button wearableOnlyActions = null;

    private int notifId = 001;
    private NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        if (getIntent() != null) {
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

    /**
     * Creates a pending notification with a title and text
     */
    private void pendingNotifications() {
        String id = "channel_1";
        String title = getString(R.string.notif_title);
        String text = getString(R.string.simple_notif_text);

        // The text is displayed on an Intent
        PendingIntent resultPendingIntent = createPendingIntent(0, text);

        // Build a notification using the Intent created above
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.ic_lightbulb_on_black_18dp)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent);

        // Display the notification
        notify(builder.build());
    }

    /**
     * Creates an action notification with a title, text and an action area
     */
    private void actionNotification() {
        String id = "channel_2";

        String title = getString(R.string.notif_title);
        String text = getString(R.string.action_button_notif_text);

        // The text is displayed on an Intent
        PendingIntent resultPendingIntent = createPendingIntent(0, text);

        // Build a notification using the Intent created above
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.ic_lightbulb_on_black_18dp)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_alert_black_18dp,
                                getString(R.string.action_button_text),
                                resultPendingIntent);

        // Display the notification
        notify(builder.build());
    }

    /**
     * Creates a wearable only action notification with a title, text and an action area
     */
    private void wearableOnlyNotification() {
        String id = "channel_3";

        String title = getString(R.string.notif_title);
        String text = getString(R.string.wearable_only_notif_text);

        // The text is displayed on an Intent
        PendingIntent resultPendingIntent = createPendingIntent(0, text);

        /*
         * To ensure the notification will be displayed on wearable only, we have to set the
         * settings using the helper class WearableExtender
         */
        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .addAction(new NotificationCompat.Action(R.drawable.ic_alert_white_18dp,
                                getString(R.string.action_button_text),
                                resultPendingIntent));

        // Build a notification using the Intent created above
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, id)
                        .setSmallIcon(R.drawable.ic_lightbulb_on_black_18dp)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .extend(wearableExtender);

        // Display the notification
        notify(builder.build());
    }

    /**
     * Asks the notification manager to display a notification
     *
     * @param notification the notification to display.
     */
    private void notify(Notification notification) {
        notificationManager = NotificationManagerCompat.from(this);
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
        if (intent == null) return;
        if (Constants.MY_PENDING_INTENT_ACTION.equals(intent.getAction())) {
            Toast.makeText(this, "" + intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method used to create a PendingIntent with the specified message
     * The intent will start a new activity Instance or bring to front an existing one.
     * See parentActivityName and launchMode options in Manifest
     * See https://developer.android.com/training/notify-user/navigation.html for TaskStackBuilder
     *
     * @param requestCode The request code
     * @param message     The message
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
}
