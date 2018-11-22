package macguffinco.hellrazorbarber;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class NotificarionReceiver extends BroadcastReceiver {
    private NotificationHelper mNotificationHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationHelper = new NotificationHelper(context);
        sendOnChannel1("My Barber","RECUERDA TU PROXIMA CITA");
            }

    private void sendOnChannel1(String tittle, String message) {
        NotificationCompat.Builder nb= mNotificationHelper.getChannel1Notification(tittle,message);
        mNotificationHelper.getmManager().notify(1,nb.build());

    }

}
