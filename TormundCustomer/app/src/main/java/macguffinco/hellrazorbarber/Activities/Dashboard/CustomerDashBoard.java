package macguffinco.hellrazorbarber.Activities.Dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import macguffinco.hellrazorbarber.Logic.TormundManager;

import macguffinco.hellrazorbarber.R;

public class CustomerDashBoard extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private static ImageView profilePic=null;
    private static TextView profileName=null;
    private static TextView profileMail=null;
    private static  NavigationView navigationView=null;
    private static Menu nav_Menu=null;
    private static String facebookToken="";
    ViewPager   viewPager;
    PagerTabStrip pagerTabStrip;
    ImageView mDashboardLogo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        //facebookToken=AccessToken.getCurrentAccessToken().getUserId();

        Adapter adapter= new Adapter(getSupportFragmentManager(),this);
        viewPager=(ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        pagerTabStrip=(PagerTabStrip)findViewById(R.id.titleTab);
        pagerTabStrip.setTabIndicatorColor(Color.parseColor("#D1AC19"));

        if(TormundManager.GlobalCustomer==null){
            TormundManager.UpdateUserData();
        }
        InitializateActivitiesControls();

        mDashboardLogo=findViewById(R.id.DashboardBranchLogo);
        if(mDashboardLogo!=null){

            if(TormundManager.GlobalBranch.VaultFileDC!=null &&
                    TormundManager.GlobalBranch.VaultFileDC.Url!=null &&
                    !TormundManager.GlobalBranch.VaultFileDC.Url.equals("")){

            }
            Picasso.with(this).load(TormundManager.GlobalBranch.VaultFileDC.Url)

                    .fit()
                    .into(mDashboardLogo);
        }

    }




    private void InitializateActivitiesControls(){


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

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

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
    public void onClick(View v) {

    }
}