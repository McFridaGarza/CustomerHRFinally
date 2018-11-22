package macguffinco.hellrazorbarber.Activities.Dates;

import android.app.TimePickerDialog;
import android.content.Context;

import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;

import android.support.design.widget.Snackbar;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Dates.DateDC;
import macguffinco.hellrazorbarber.R;
import macguffinco.hellrazorbarber.Services.Branches.AdapterBranch;

public class DatesBrunchActivity extends AppCompatActivity implements OnNavigationItemSelectedListener{


    Button btnhora;
    TextView txthora;
    private int hora, minutos;
    private static  NavigationView navigationView=null;
    private static Menu nav_Menu=null;
    public static DateDC ActiveDate;
    ArrayList<BranchDC> branchesList;
    RecyclerView recyclerBranch;
    private String lastTagSelected;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context=getApplicationContext();
        this.ActiveDate=null;
        lastTagSelected="";
        GetPendingAppointment();


    }

    private void eventsTabs(){

        FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void InitializateActivitiesControls(){

        setContentView( R.layout.activity_dates_brunch);

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

    private void GetPendingAppointment(){

        /*if(TormundManager.GlobalCustomer!=null){
            new DatesActivityTask(this,this).execute(new DateDC(),null,null);
        }*/

    }

    public void InitializateTabsControl(){
        /*TABS */
        Resources res = getResources();

        final TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
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

        if(ActiveDate!=null){
            tabs.setCurrentTab(ActiveDate.SubStatus==0?0:(ActiveDate.SubStatus-1));
        }else{
            tabs.setCurrentTab(0);
        }


        tabs.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String currentTag=tabs.getCurrentTabTag();
                 if(ValidateEnabledTab(lastTagSelected,currentTag)){
                     tabs.setCurrentTabByTag(currentTag);
                 } else {
                    tabs.setCurrentTabByTag(lastTagSelected);

                     Toast msg=Toast.makeText(v.getContext(), "Falta configurar información.", Toast.LENGTH_SHORT);
                     msg.show();
                }
                lastTagSelected=currentTag;
            }
        });



        btnhora.setOnClickListener(handleButtonClickHour);





    }

    private View.OnClickListener handleButtonClickHour = new View.OnClickListener() {
        public void onClick(View view) {
            if(view==btnhora){
                final Calendar c= Calendar.getInstance();
                hora=c.get(Calendar.HOUR_OF_DAY);
                minutos=c.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txthora.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hora, minutos, true);//Yes 24 hour time
               // mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }

        }
    };



    private boolean ValidateEnabledTab(String currentTab,String selectedTabTag){
        if(ActiveDate!=null && !selectedTabTag.isEmpty()){
            int numTab=0;
            try {
                numTab=Integer.parseInt(selectedTabTag);
            }catch (Exception e){
                numTab=0;
            }

            if(numTab>0){
                int subStatus=ActiveDate.Status;
                if(numTab>subStatus){
                    return false;
                }
            }
        }

        return true;
    }

    private void EnabledLogOutButton(){

        if (nav_Menu==null) return;

        if(TormundManager.ValidateState()){
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        }else{
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        }

    }

    public void BranchesTab(){
        final Context context=this;
        final DatesBrunchActivity activity=this;
        //RECYCLER VIEW//
        recyclerBranch=(RecyclerView)findViewById(R.id.recyclerId);
        recyclerBranch.setLayoutManager(new LinearLayoutManager(this));
        branchesList=new ArrayList<BranchDC>();

       FillBranches();

        recyclerBranch.setAdapter(new AdapterBranch(branchesList, new AdapterBranch.OnItemClickListener() {
            @Override public void onItemClick(BranchDC branchDC) {

               // new DatesActivityTask(context,activity).execute(dateDC,null,null);
            }
        }));

    }



    public void FillBranches() {

      branchesList=TormundManager.GlobalBranches;

     /*   branchesList.add(new BranchDC("CENTRITO VALLE","",R.drawable.ic_menu_camera,"83531521",new AddressDC(0,"Rio Grijalva","asdf",1,true,"")));
        branchesList.add(new BranchDC("CUMBRES","",R.drawable.ic_menu_camera,"80282839",new AddressDC(0,"Av. Leones","3424",1,true,"")));
        branchesList.add(new BranchDC("SAN NICOLAS","",R.drawable.ic_menu_camera,"21640577",new AddressDC(0,"Av. Universidad","324234",1,true,"")));
        branchesList.add(new BranchDC("test","",R.drawable.ic_menu_camera,"21640577",new AddressDC(0,"Av. Universidad","324324",1,true,"")));
*/

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


