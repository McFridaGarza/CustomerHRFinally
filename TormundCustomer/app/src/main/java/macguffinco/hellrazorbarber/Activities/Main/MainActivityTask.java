package macguffinco.hellrazorbarber.Activities.Main;

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
import macguffinco.hellrazorbarber.R;

public class MainActivityTask extends AsyncTask<String, Integer, Long> {

    private Context context;
    private MainActivity activity;
    private static String email = "";
    private static String name ="";
    private static String birthday = "";
    private static ImageView profilePic=null;
    private static TextView profileName=null;
    private static TextView profileMail=null;
    private static NavigationView navigationView=null;
    private static Menu nav_Menu=null;
    RequestQueue requestQueue;




    public MainActivityTask(Context context,MainActivity activity){
        this.context = context;
        this.activity=activity;
    }
    protected Long doInBackground(String... data) {


        if(TormundManager.GlobalCustomer==null){
            TormundManager.UpdateUserData();
        }

        return 0L;
    }

    protected void onProgressUpdate(Integer... progress) {
        String hola="";
    }

    protected void onPostExecute(Long result) {

        RedirectActiviy();
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
        TormundManager.SyncronizeProfileData(navigationView,activity);
        TormundManager.EnabledLogOutButton(nav_Menu);


    }

    public void RedirectActiviy(){

        if(TormundManager.ValidateState()){
            TormundManager.goDashboardScreen(activity);
        }
    }





}