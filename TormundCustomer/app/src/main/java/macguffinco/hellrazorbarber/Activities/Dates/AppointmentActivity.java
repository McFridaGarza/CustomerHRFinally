package macguffinco.hellrazorbarber.Activities.Dates;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Dates.DateDC;
import macguffinco.hellrazorbarber.Model.Dates.Status;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.R;
import macguffinco.hellrazorbarber.Services.Branches.AdapterBranch;
import macguffinco.hellrazorbarber.Services.Services.AdapterServices;
import macguffinco.hellrazorbarber.Services.Users.AdapterEmployees;

public class AppointmentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private int hora, minutos;
    TextView CheckTabServiceName;
    TextView CheckTabDate;
    TextView CheckTabDateInitHour;
    TextView CheckTabDateFInishedHour;
    TextView CheckTabBarber;
    TextView CheckTabTotal;
    ImageView CheckTabBarberImage;

    private static  NavigationView navigationView=null;
    private static Menu nav_Menu=null;

    ArrayList<BranchDC> branchesList;
    ArrayList<ServiceDC> serviceList;
    ArrayList<EmployeeDC> barberList;
    ArrayList<DateDC> enabledHoursList;
    public int yearSelected=0;
    public int monthSelected=0;
    public int daySelected=0;
    RecyclerView recyclerBranch;
    RecyclerView recyclerServices;
    RecyclerView recyclerSBarbers;
    RecyclerView recyclerEnablesHours;
    CalendarView calendarView;
    TimePickerDialog timePickerDialog;
    private String lastTagSelected;
    public Context context;
    public AppointmentActivity activity;
    TabHost tabs=null;
    final  int BRANCHES_TAB=0;
    final  int SERVICES_TAB=1;
    final  int BARBERS_TAB=2;
    final  int DATES_TAB=3;
    final  int CONFIRMATION_TAB=4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_appointment);

        context=getApplicationContext();
        activity=this;
        TormundManager.GlobalNewAppointment=new DateDC();
        if(TormundManager.GlobalCustomer!=null){
            TormundManager.GlobalNewAppointment.Status= Status.Active.getValue();
            TormundManager.GlobalNewAppointment.Customer=TormundManager.GlobalCustomer;
        }
        lastTagSelected="";
        //GetPendingAppointment();

        activity.InitializateActivitiesControls();
        activity.BranchesTab();
        activity.ServiceTab();
        activity.BarbersTab();
        activity.DatesTab();
        activity.ConfirmationTab();
        activity.OnClickEventForTabs();





    }



    public void InitializateActivitiesControls(){



        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu= navigationView!=null?navigationView.getMenu():null;
        TormundManager.SyncronizeProfileData(navigationView,getApplicationContext());
        TormundManager.EnabledLogOutButton(nav_Menu);






        InitializateTabsControl();



    }


    public void ShowDialogAlert( ){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // set title
        alertDialogBuilder.setTitle("Disponible para Citas");



        final String[] horaries=new String[enabledHoursList.size()];

        for (int i =0;i<enabledHoursList.size(); i++     ) {

         horaries[i]=TormundManager.JsonDateToHours(enabledHoursList.get(i).AppointmentDate)+"-"+TormundManager.JsonDateToHours(enabledHoursList.get(i).DueDate);

        }

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                })

                .setItems(horaries, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try{

                            DateDC dateSelected = enabledHoursList.get(which);
                            if(dateSelected!=null){

                                Calendar cal = Calendar.getInstance(); // creates calendar
                                cal.setTime(dateSelected.AppointmentDate); // sets calendar time/date
                                cal.add(Calendar.MINUTE, (int)TormundManager.GlobalNewAppointment.Service.servicetime); // adds one hour
                                Date dueDate=cal.getTime();

                                TormundManager.GlobalNewAppointment.SubStatus=CONFIRMATION_TAB;
                                TormundManager.GlobalNewAppointment.Status=4;
                                TormundManager.GlobalNewAppointment.status_list="4";
                                TormundManager.GlobalNewAppointment.AppointmentDate=dateSelected.AppointmentDate;
                                TormundManager.GlobalNewAppointment.DueDate=dueDate;
                                String message=TormundManager.JsonDateToHours(enabledHoursList.get(which).AppointmentDate)
                                        +"-"+TormundManager.JsonDateToHours(dueDate);


                                dialog.cancel();
                                // new DatesActivityTask(context,activity,"UpdateAppointmentDate").execute(new DateDC(),null,null);

                                UpdateConfirmationTab();
                                tabs.setCurrentTab(CONFIRMATION_TAB);


                            }
                        }catch (Exception ex){
                            ex.printStackTrace();
                            Toast msg=Toast.makeText(context, "Ocurrio un error.", Toast.LENGTH_SHORT);
                            msg.show();
                            finish();
                        }



                    }
        });


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    private void GetPendingAppointment(){

        if(TormundManager.GlobalCustomer!=null){
            new DatesActivityTask(this,this,"PendingAppointent").execute(new DateDC(),null,null);
        }

    }

    public void InitializateTabsControl(){
        /*TABS */
        Resources res = getResources();

        tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();
        //PRIMER TAB
        TabHost.TabSpec spec=tabs.newTabSpec("1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("",getResources().getDrawable(R.drawable.ic_map));
        tabs.addTab(spec);
        //SEGUNDO TAB
        spec=tabs.newTabSpec("2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("",getResources().getDrawable(R.drawable.ic_content_cut));
        tabs.addTab(spec);
        //TERCER TAB

        spec=tabs.newTabSpec("3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("",getResources().getDrawable(R.drawable.ic_star));
        tabs.addTab(spec);

        //CUARTO TAB

        spec=tabs.newTabSpec("4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("",getResources().getDrawable(R.drawable.ic_calendar_clock));
        tabs.addTab(spec);

        //QUINTO TAB

        spec=tabs.newTabSpec("5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("",getResources().getDrawable(R.drawable.ic_check));
        tabs.addTab(spec);

        if(TormundManager.GlobalNewAppointment!=null){
            tabs.setCurrentTab(TormundManager.GlobalNewAppointment.SubStatus);
        }else{
            tabs.setCurrentTab(0);
        }







    }



    private boolean ValidateEnabledTab(String currentTab,String selectedTabTag){
        if(TormundManager.GlobalNewAppointment!=null && !selectedTabTag.isEmpty()){
            int numTab=0;
            try {
                numTab=Integer.parseInt(selectedTabTag);
            }catch (Exception e){
                numTab=0;
            }

            if(numTab>0){
                int subStatus=TormundManager.GlobalNewAppointment.Status;
                if(numTab>subStatus){
                    return false;
                }
            }
        }

        return true;
    }



    public void BranchesTab(){
        final Context context=this;
        final AppointmentActivity activity=this;
        //RECYCLER VIEW//
        recyclerBranch=(RecyclerView)findViewById(R.id.recyclerId);
        recyclerBranch.setLayoutManager(new LinearLayoutManager(this));
        branchesList=new ArrayList<BranchDC>();

        FillBranches();

        recyclerBranch.setAdapter(new AdapterBranch(branchesList, new AdapterBranch.OnItemClickListener() {
            @Override public void onItemClick(BranchDC branchDC) {

                TormundManager.GlobalNewAppointment.SubStatus=SERVICES_TAB;
                TormundManager.GlobalNewAppointment.Status=4;
                TormundManager.GlobalNewAppointment.status_list="4";
                TormundManager.GlobalNewAppointment.Branch=branchDC;
            /*    Thread tr= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TormundManager.GlobalNewAppointment=DatesManager.UpdateDate(TormundManager.GlobalNewAppointment);
                    }
                });
                tr.start();*/


                tabs.setCurrentTab(SERVICES_TAB);
                // new DatesActivityTask(context,activity).execute(dateDC,null,null);
            }
        }));

    }

    public void FillBranches() {

        branchesList=TormundManager.GlobalBranches;


    }

    public void ServiceTab(){
        final Context context=this;
        final AppointmentActivity activity=this;
        //RECYCLER VIEW//
        recyclerServices=(RecyclerView)findViewById(R.id.recyclerServicesId);
        recyclerServices.setLayoutManager(new LinearLayoutManager(this));
        serviceList=new ArrayList<ServiceDC>();

        FillServices();

        recyclerServices.setAdapter(new AdapterServices(serviceList, new AdapterServices.OnItemClickListener() {
            @Override public void onItemClick(ServiceDC serviceDC) {

                TormundManager.GlobalNewAppointment.SubStatus=BARBERS_TAB;
                TormundManager.GlobalNewAppointment.Status=4;
                TormundManager.GlobalNewAppointment.status_list="4";
                TormundManager.GlobalNewAppointment.Service=serviceDC;
               /* Thread tr= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TormundManager.GlobalNewAppointment=DatesManager.UpdateDate(TormundManager.GlobalNewAppointment);
                    }
                });
                tr.start();*/


                tabs.setCurrentTab(BARBERS_TAB);
                // new DatesActivityTask(context,activity).execute(dateDC,null,null);
            }
        }));

    }

    public void FillServices() {

        serviceList=TormundManager.GlobalServices;

    }

    public void BarbersTab(){
        final Context context=this;
        final AppointmentActivity activity=this;
        //RECYCLER VIEW//
        recyclerSBarbers=(RecyclerView)findViewById(R.id.recyclerBarberList);
        recyclerSBarbers.setLayoutManager(new LinearLayoutManager(this));
        barberList=new ArrayList<EmployeeDC>();

        FillSBarbers();

        recyclerSBarbers.setAdapter(new AdapterEmployees(barberList, new AdapterEmployees.OnItemClickListener() {
            @Override public void onItemClick(EmployeeDC barberDC) {

                TormundManager.GlobalNewAppointment.SubStatus=DATES_TAB;
                TormundManager.GlobalNewAppointment.Status=4;
                TormundManager.GlobalNewAppointment.status_list="4";
                TormundManager.GlobalNewAppointment.Employee=barberDC;


                tabs.setCurrentTab(DATES_TAB);

            }
        }));

    }

    public void FillSBarbers() {

        barberList=TormundManager.GlobalBarbers;

    }

    public void DatesTab(){
        calendarView=(CalendarView) findViewById(R.id.calendarView);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,Calendar.getInstance().getActualMinimum(Calendar.DATE));
        long date = calendar.getTime().getTime();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                yearSelected=year;
                monthSelected=month+1;
                daySelected=dayOfMonth;
                new DatesActivityTask(context,activity,"EnabledHours").execute(new DateDC(),null,null);


            }
        });
    }

    public void ConfirmationTab(){


        CheckTabServiceName=(TextView) findViewById(R.id.CheckTabServiceName);
        CheckTabDate=(TextView) findViewById(R.id.CheckTabDate);
        CheckTabDateInitHour=(TextView) findViewById(R.id.CheckTabDateInitHour);
        CheckTabDateFInishedHour=(TextView) findViewById(R.id.CheckTabDateFInishedHour);
        CheckTabBarber=(TextView) findViewById(R.id.CheckTabBarber);
        CheckTabTotal= (TextView) findViewById(R.id.CheckTabTotal);





    }

    public void OnClickEventForTabs(){

        for (int i=0;i<tabs.getTabWidget().getTabCount();i++){

            tabs.getTabWidget().getChildTabViewAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String currentTag=tabs.getCurrentTabTag();
//                    if(ValidateEnabledTab(lastTagSelected,currentTag)){
//                        tabs.setCurrentTabByTag(currentTag);
//                    } else {
//                        tabs.setCurrentTabByTag(lastTagSelected);

                        Toast msg=Toast.makeText(v.getContext(), "Falta configurar información.", Toast.LENGTH_SHORT);
                        msg.show();
                    tabs.setCurrentTabByTag(currentTag);
//                    }
//                    lastTagSelected=currentTag;
                }
            });

        }

    }


    public void onClickBtnConfirmationDate(View view)
    {
        int id = view.getId();

        new DatesActivityTask(context,activity,"CreateNewAppointment").execute(new DateDC(),null,null);



    }

    public void UpdateConfirmationTab(){
        if(TormundManager.GlobalNewAppointment!=null){
            if(TormundManager.GlobalNewAppointment.Service!=null){
                CheckTabServiceName.setText(TormundManager.GlobalNewAppointment.Service.Name.toUpperCase());
                CheckTabTotal.setText(Double.toString(TormundManager.GlobalNewAppointment.Service.Price));
            }

            if(TormundManager.GlobalNewAppointment.AppointmentDate!=null){
                CheckTabDate.setText("Fecha: "+TormundManager.FormatDate(TormundManager.GlobalNewAppointment.AppointmentDate));
            }
            if(TormundManager.GlobalNewAppointment.AppointmentDate!=null){
                CheckTabDateInitHour.setText("Comienza: "+TormundManager.FormatHour(TormundManager.GlobalNewAppointment.AppointmentDate));
            }

            if(TormundManager.GlobalNewAppointment.DueDate!=null){
                CheckTabDateFInishedHour.setText("Termina: "+TormundManager.FormatHour(TormundManager.GlobalNewAppointment.DueDate));
            }

            if(TormundManager.GlobalNewAppointment.Employee!=null){
                CheckTabBarber.setText("Barbero: "+TormundManager.GlobalNewAppointment.Employee.name);
            }

            if(TormundManager.GlobalNewAppointment.Employee!=null && TormundManager.GlobalNewAppointment.Employee.vault_file_id!=null){
                Picasso.with(context).load(TormundManager.GlobalNewAppointment.Employee.vault_file_id.Url).into(CheckTabBarberImage);


            }



        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        TormundManager.onNavigationItemSelected(this,item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        finish();
        /**DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         if (drawer.isDrawerOpen(GravityCompat.START)) {
         drawer.closeDrawer(GravityCompat.START);
         } else {

         }*/
    }


}
