package macguffinco.hellrazorbarber;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


import macguffinco.hellrazorbarber.Activities.Main.MainActivity;

import static macguffinco.hellrazorbarber.R.drawable.ic_mybarber;

public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID ="channel1ID";
    public static final String channel1Name="Channel 1";
      private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }
       @TargetApi(Build.VERSION_CODES.O)
       public void createChannels(){
            NotificationChannel  channel1= new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setLightColor(R.color.colorPrimary);
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getmManager().createNotificationChannel(channel1);


       }
public NotificationManager getmManager(){
if(mManager == null){
    mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
}
return mManager;
}




    public NotificationCompat.Builder getChannel1Notification(String title,String message){

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return new NotificationCompat.Builder(getApplicationContext(),channel1ID)
        .setContentTitle(title)
        .setContentText(message)
        .setAutoCancel(true)
                 .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(Color.BLUE)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[] {100, 250, 100, 500})
        .setSmallIcon(ic_mybarber);

    }



}

