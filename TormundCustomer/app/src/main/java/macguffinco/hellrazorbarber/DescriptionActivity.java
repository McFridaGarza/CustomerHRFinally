package macguffinco.hellrazorbarber;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


public class DescriptionActivity extends AppCompatActivity {


    private Button btnNotif1;
    private NotificationHelper mNotificationHelper;
    TextView close_detail;
    private TextView mtittle, mdesciption, mprice;
    private ImageView mimg;
    String texto = "";
    String notificacion = "";
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        AlarmManager objAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar objCalendar = Calendar.getInstance();
        objCalendar.set(Calendar.YEAR, 2018);
        objCalendar.set(Calendar.YEAR, objCalendar.get(Calendar.YEAR));
        objCalendar.set(Calendar.MONTH, 9);
        objCalendar.set(Calendar.DAY_OF_MONTH, 26);
        objCalendar.set(Calendar.HOUR_OF_DAY, 10);
        objCalendar.set(Calendar.MINUTE, 30);
        objCalendar.set(Calendar.SECOND, 0);
        objCalendar.set(Calendar.MILLISECOND, 0);
        objCalendar.set(Calendar.AM_PM, Calendar.AM);
        Intent alamShowIntent = new Intent(this,NotificarionReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this, 0,alamShowIntent,0 );
        objAlarmManager.set(AlarmManager.RTC_WAKEUP,objCalendar.getTimeInMillis(), alarmPendingIntent);
       /* alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificarionReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 10);
        String tiempo = calendar.getTime().toString();
        if (tiempo.equals("")) {

        }
// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 5, alarmIntent);*/


        btnNotif1 = findViewById(R.id.btnotificacion1);
        btnNotif1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOnChannel1(texto = "HELL RAZOR", notificacion = "RECUERDA TU PROXIMA CITA");
            }

        });

        try {

            close_detail = findViewById(R.id.close_detail);

            mtittle = (TextView) findViewById(R.id.titulomdescipcion);
            mdesciption = (TextView) findViewById(R.id.descriptionmarket);
            mprice = (TextView) findViewById(R.id.marketprice);
            mimg = (ImageView) findViewById(R.id.imgmarketdescription);

            Intent intent2 = getIntent();
            String Tittle = intent2.getExtras().getString("Titulo");
            String Description = intent2.getExtras().getString("Descripcion");
            String Price = intent2.getExtras().getString("Precio");
            int image = intent2.getExtras().getInt("Imagen");

            mtittle.setText(Tittle);
            mprice.setText(Price);
            mdesciption.setText(Description);
            mimg.setImageResource(image);

            close_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception ex) {
            String Hola = "";
            if (Hola.equals(" ")) {

            }
        }


    }

    private void sendOnChannel1(String tittle, String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(tittle, message);
        mNotificationHelper.getmManager().notify(1, nb.build());

    }


}


