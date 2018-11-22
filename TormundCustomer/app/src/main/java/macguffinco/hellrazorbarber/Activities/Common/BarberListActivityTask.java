package macguffinco.hellrazorbarber.Activities.Common;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.R;


class BarberListActivityTask extends AsyncTask<EmployeeDC, Integer, Long> {

    private Context context;
    private BarbersListActivity activity;
    private static ImageView profilePic=null;
    private static TextView profileName=null;
    private static TextView profileMail=null;
    private static NavigationView navigationView=null;
    static EmployeeDC barberDC;
    private static Menu nav_Menu=null;
    RequestQueue requestQueue;

    public BarberListActivityTask(Context context,BarbersListActivity activity){
        this.context = context;
        this.activity=activity;
    }
    protected Long doInBackground(EmployeeDC... serviceDC) {

        activity.recyclerBarbers.setAdapter(new macguffinco.hellrazorbarber.Services.Users.AdapterEmployees(activity.barberList, new macguffinco.hellrazorbarber.Services.Users.AdapterEmployees.OnItemClickListener() {
            @Override public void onItemClick(EmployeeDC barberDC) {


            }
        }));

        return 0L;
    }

    protected void onProgressUpdate(Integer... progress) {
        String hola="";
    }

    protected void onPostExecute(Long result) {

        InitializateActivitiesControls();


    }

    public void InitializateActivitiesControls(){


        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);

        activity.setSupportActionBar(toolbar);
        requestQueue = Volley.newRequestQueue(context);


        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(activity);
        nav_Menu= navigationView!=null?navigationView.getMenu():null;
        TormundManager.SyncronizeProfileData(navigationView,context);
        TormundManager.EnabledLogOutButton(nav_Menu);


    }





}