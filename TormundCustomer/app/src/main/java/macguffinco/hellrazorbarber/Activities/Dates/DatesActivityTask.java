package macguffinco.hellrazorbarber.Activities.Dates;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.sql.Timestamp;
import java.util.ArrayList;

import macguffinco.hellrazorbarber.Logic.DatesManager;
import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Dates.DateDC;
import macguffinco.hellrazorbarber.R;

public class DatesActivityTask extends AsyncTask<DateDC, Integer, DateDC> {

    private Context context;
    private AppointmentActivity activity;
    private String event="";

    private static ImageView profilePic=null;
    private static TextView profileName=null;
    private static TextView profileMail=null;
    private static NavigationView navigationView=null;

    static BranchDC branchDC;
    private static Menu nav_Menu=null;
    RequestQueue requestQueue;

    public DatesActivityTask(Context context,AppointmentActivity activity,String event){
        this.context = context;
        this.activity=activity;
        this.event=event;
    }




    protected DateDC  doInBackground(DateDC... dateDC) {

        DateDC date=null;
        if(TormundManager.GlobalCustomer==null){
            TormundManager.UpdateUserData();
        }

       switch (event){
           case "PendingAppointent": {

               //TormundManager.GlobalNewAppointment=DatesManager.GetCustomerPendingDate(TormundManager.GlobalCustomer);
               break;
           }
           case "EnabledHours": {

               activity.enabledHoursList=new ArrayList<DateDC>();
               DateDC datedc= new DateDC();
               datedc.AppointmentDate= Timestamp.valueOf(activity.yearSelected+"-"+activity.monthSelected+"-"+activity.daySelected+" 00:00:00.000");
               datedc.Customer=TormundManager.GlobalCustomer;
               datedc.Branch=TormundManager.GlobalNewAppointment.Branch;
               datedc.Employee=TormundManager.GlobalNewAppointment.Employee;
               datedc.Service=TormundManager.GlobalNewAppointment.Service;
               activity.enabledHoursList=DatesManager.getEnabledDates(datedc);
               break;
           }

           case "UpdateAppointmentDate": {

               TormundManager.GlobalNewAppointment=DatesManager.UpdateDate(TormundManager.GlobalNewAppointment);
               activity.UpdateConfirmationTab();

               break;
           }

           case "CreateNewAppointment": {
               TormundManager.GlobalNewAppointment=DatesManager.CreateNewAppointment(TormundManager.GlobalNewAppointment);



               break;
           }

           case "ConfirmationDate":{


               TormundManager.GlobalNewAppointment.Status=1;
               TormundManager.GlobalNewAppointment.SubStatus=activity.CONFIRMATION_TAB;
               TormundManager.GlobalNewAppointment=DatesManager.UpdateDate(TormundManager.GlobalNewAppointment);




               break;
           }


           default:{

               TormundManager.GlobalNewAppointment=DatesManager.GetCustomerPendingDate(TormundManager.GlobalCustomer);
           }
       }




        return date;
    }

    protected void onProgressUpdate(Integer... progress) {
        String hola="";
    }

    protected void onPostExecute(DateDC result) {

        switch (event){
            case "PendingAppointent": {

                activity.InitializateActivitiesControls();
                activity.BranchesTab();
                activity.ServiceTab();
                activity.BarbersTab();
                activity.DatesTab();
                activity.ConfirmationTab();
                break;
            }
            case "EnabledHours": {

                activity.ShowDialogAlert();
                break;
            }
            case "UpdateAppointmentDate": {

               activity.tabs.setCurrentTab(activity.CONFIRMATION_TAB);

                break;}

            case "CreateNewAppointment": {

                activity.UpdateConfirmationTab();
                TormundManager.UpdateUserData();
                activity.finish();

                break;}

            case "ConfirmationDate":{

            break;
            }

            default:{

                activity.InitializateActivitiesControls();
                activity.BranchesTab();
                activity.ServiceTab();
                activity.BarbersTab();
                activity.DatesTab();
                activity.ConfirmationTab();
                break;
            }
        }



    }



    public void EnabledLogOutButton(){

        if (nav_Menu==null) return;

        if(TormundManager.ValidateState()){
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        }else{
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        }

    }


}