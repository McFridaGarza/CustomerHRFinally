package macguffinco.hellrazorbarber.Activities.Common;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import macguffinco.hellrazorbarber.Logic.CropCircleTransformation;
import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Activities.Main.MainActivity;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.R;
import macguffinco.hellrazorbarber.Services.Branches.AdapterBranch;

public class BranchesListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{


    private static ImageView imgHeader=null;
    private static TextView profileName=null;
    private static TextView profileMail=null;
    public Context context= null;
    ArrayList<BranchDC> branchesList;
    RecyclerView recyclerBranch;
    private static  NavigationView navigationView=null;
    private static Menu nav_Menu=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches_list);
        context=this;
        final BranchesListActivity activity=this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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


        //RECYCLER VIEW//
        recyclerBranch=(RecyclerView)findViewById(R.id.recyclerBranchesList);
        recyclerBranch.setLayoutManager(new LinearLayoutManager(this));
        branchesList=new ArrayList<BranchDC>();

        FillBranches();
        recyclerBranch.setAdapter(new AdapterBranch(branchesList, new AdapterBranch.OnItemClickListener() {
            @Override public void onItemClick(BranchDC branchDC) {

                new BranchesListActivityTask(context,activity).execute(branchDC,null,null);
            }
        }));

        imgHeader=findViewById(R.id.imgHeaderBranches);
        Picasso.with(this)
                .load(TormundManager.GlobalBranch.VaultFileDC.Url)
                .fit()
                .into(imgHeader);


    }







    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
        else
            finish();


    }

    public void FillBranches() {

        branchesList=TormundManager.GlobalBranches;
    }





    private void goMainScreen(){
        Intent intent= new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public void onClick(View view) {

        int id = view.getId();


       // if (id == R.id.buttonLogIn) {
         //   goLoginScreen();

        //}
    }



    @Override
    public void onRestart(){
        super.onRestart();
      TormundManager.goMainScreen(context);

    }
}

class BranchesListActivityTask extends AsyncTask<BranchDC, Integer, Long> {

    private Context context;
    private BranchesListActivity activity;
    private static ImageView profilePic=null;
    private static TextView profileName=null;
    private static TextView profileMail=null;
    private static  NavigationView navigationView=null;
    static BranchDC branchDC;
    private static Menu nav_Menu=null;
    RequestQueue requestQueue;

    public BranchesListActivityTask(Context context,BranchesListActivity activity){
        this.context = context;
        this.activity=activity;
    }
    protected Long doInBackground(BranchDC... branchDC) {



        activity.recyclerBranch.setAdapter(new AdapterBranch(activity.branchesList, new AdapterBranch.OnItemClickListener() {
            @Override public void onItemClick(BranchDC branchDC) {


            }
        }));

        return 0L;
    }

    protected void onProgressUpdate(Integer... progress) {
        String hola="";
    }

    protected void onPostExecute(Long result) {

        InitializateActivitiesControls();
        EnabledLogOutButton();
        SyncronizeProfileData();

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



    }

    public void RedirectActiviy(){

        if(TormundManager.ValidateState()){
            //goDashboardScreen();
    }
    }


    public void SyncronizeProfileData(){
        if (navigationView==null) return;
        //Header NavView
        View header = navigationView.getHeaderView(0);
        if(header!=null){


            profilePic = (ImageView) header.findViewById(R.id.ProfileViewFace);
            profileName = (TextView) header.findViewById(R.id.ProfileName);
            profileMail = (TextView) header.findViewById(R.id.ProfileMail);
            profileName.setText(TormundManager.name);
            profileMail.setText(TormundManager.emailFacebook);

            Picasso.with(activity)
                    .load("https://graph.facebook.com/v2.2/" + TormundManager.facebookId + "/picture?height=250&type=normal") //extract as User instance method
                    .transform(new CropCircleTransformation())
                    .resize(250, 250)
                    .into(profilePic);
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
