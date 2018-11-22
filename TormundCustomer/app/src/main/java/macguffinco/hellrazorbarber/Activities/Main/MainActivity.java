package macguffinco.hellrazorbarber.Activities.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import macguffinco.hellrazorbarber.Activities.Dashboard.CustomerDashBoard;
import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Comercial.CustomerDC;
import macguffinco.hellrazorbarber.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private ImageView logoimg;
    private LoginButton loginButton;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  cargarimagen();
        setContentView(R.layout.activity_main);


        logoimg=findViewById(R.id.logoimg);
        Picasso.with(this)
                .load(TormundManager.GlobalBranch.VaultFileDC.Url)
                .fit()
                .into(logoimg);



        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

                TormundManager.GlobalUser.TokenFacebook=loginResult.getAccessToken().getUserId();
                TormundManager.MenuProfileActions(loginResult.getAccessToken());
                goDashboardScreen();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TormundManager.GlobalUser.Name=TormundManager.name;
                        TormundManager.GlobalUser.EmailFacebook=TormundManager.emailFacebook;
                        CustomerDC customerDC= new CustomerDC();
                        customerDC.name=TormundManager.name;
                        customerDC.born_date=TormundManager.JsonStringToDate3(TormundManager.birthday);
                        customerDC.principal_email=TormundManager.emailFacebook;
                        customerDC.branch=TormundManager.GlobalBranch;
                        TormundManager.GlobalUser.customer=customerDC;
                        TormundManager.GlobalUser=TormundManager.ValidateCreatedUser(TormundManager.GlobalUser);


                        TormundManager.UpdateUserData();
                        if(TormundManager.GlobalUser.TokenFacebook!=null && TormundManager.GlobalUser.TokenFacebook!=""){
                            //TormundManager.SaveFacebookProfilePicture(TormundManager.GlobalUser.TokenFacebook);
                        }
                        goDashboardScreen();

                    }
                });
                thread.start();

            }

            @Override
            public void onCancel() {
                // App code

                String hola= "";
                if(hola.equals("")){
                    String candcion="";


                }
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                String error="";
                error=exception.toString();
            }


        });

            new MainActivityTask(this,this).execute("", "", "");



    }

   /* private void cargarimagen() {
        ImageView imglogo =(ImageView)findViewById(R.id.logoimg);
        if(GlobalCompany.Name=="Hell razor"){
            Picasso.with(MainActivity.this)
                    .load("http://hellrazor")
                    .transform(new CropCircleTransformation())
                    .resize(250, 250)
                    .into(imglogo);
        }
    else
        {
                Picasso.with(MainActivity.this)
                        .load("http://patron")
                        .transform(new CropCircleTransformation())
                        .resize(250, 250)
                        .into(imglogo);
            }
        }*/



    public void onClickFakeButton(View view){
            loginButton.performClick();

    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }





    private void goDashboardScreen(){
        Intent intent= new Intent(this, CustomerDashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goMainScreen(){
        Intent intent= new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        return;
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
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

        if(id==R.id.nav_logIn){
          loginButton.performClick();
        }else{
            TormundManager.onNavigationItemSelected(this,item.getItemId());
        }





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View view) {


    }

    public void onClickBtn(View view)
    {
        int id = view.getId();


       /* if (id == R.id.buttonLogIn) {
            goLoginScreen();

        }else{
            goDatesScreen();
        }*/
    }

    @Override
    public void onRestart(){
        super.onRestart();
       goMainScreen();

    }
}


