package macguffinco.hellrazorbarber.Activities.Common;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.R;

public class BarbersListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private static ImageView imgHeader=null;
    private static TextView profileName=null;
    private static TextView profileMail=null;
    public Context context=null;

    ArrayList<EmployeeDC> barberList;
    RecyclerView recyclerBarbers;
    private static NavigationView navigationView=null;
    private static Menu nav_Menu=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barbers_list);
        context=this;
        final Context context=this;
        final BarbersListActivity activity=this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        nav_Menu= navigationView!=null?navigationView.getMenu():null;
        TormundManager.SyncronizeProfileData(navigationView,getApplicationContext());
        TormundManager.EnabledLogOutButton(nav_Menu);


        //RECYCLER VIEW//
        recyclerBarbers=(RecyclerView)findViewById(R.id.recyclerBarberList);
        recyclerBarbers.setLayoutManager(new LinearLayoutManager(this));
        barberList=new ArrayList<EmployeeDC>();

        FillBarbers();
        recyclerBarbers.setAdapter(new macguffinco.hellrazorbarber.Services.Users.AdapterEmployees(barberList, new macguffinco.hellrazorbarber.Services.Users.AdapterEmployees.OnItemClickListener() {
            @Override public void onItemClick(EmployeeDC barberDC) {

                new BarberListActivityTask(context,activity).execute(barberDC,null,null);
            }
        }));

        imgHeader=findViewById(R.id.imgHeaderEmployees);
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

    public void FillBarbers() {

        barberList=TormundManager.GlobalBarbers;
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
        //    TormundManager.goLoginScreen(context);

        //}
    }

    public void onClickBtn(View view)
    {
        int id = view.getId();


       // if (id == R.id.buttonLogIn) {
        //    TormundManager.goLoginScreen(context);

        //}
    }

    @Override
    public void onRestart(){
        super.onRestart();
        TormundManager.goMainScreen(context);

    }
}
