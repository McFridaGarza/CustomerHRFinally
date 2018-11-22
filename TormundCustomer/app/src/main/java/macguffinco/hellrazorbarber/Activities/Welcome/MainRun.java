package macguffinco.hellrazorbarber.Activities.Welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.facebook.AccessToken;


import java.util.ArrayList;

import macguffinco.hellrazorbarber.Logic.BranchesManager;
import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Activities.Main.MainActivity;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.R;
import macguffinco.hellrazorbarber.Services.Services.ServicesServices;
import macguffinco.hellrazorbarber.Services.Users.EmployeesService;

public class MainRun extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    VideoView videoVG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_run);

//        try {
//            AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
//                @Override
//                public void onComplete(AWSStartupResult awsStartupResult) {
//                    Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
//                }
//            }).execute();
//        }catch (Exception er){
//            String hola= er.getMessage();
//            if(hola.isEmpty()){
//
//            }
//        }



        new ActivityTask(this).execute("", "", "");

    }
}



 class ActivityTask extends AsyncTask<String, Integer, Long> {

    private Context context;

    public ActivityTask(Context context){
        this.context = context;
    }
    protected Long doInBackground(String... data) {


        SharedPreferences preferences= context.getSharedPreferences("TormundStylist", Context.MODE_PRIVATE);
        String branchCode=preferences.getString("BranchCode","");

        if(!branchCode.equals("")){

            TormundManager.GlobalBranches=new ArrayList<BranchDC>();
            TormundManager.GlobalCompany=null;
            TormundManager.GlobalServices=new ArrayList<ServiceDC>();
            TormundManager.GlobalBarbers=new ArrayList<EmployeeDC>();

            BranchDC branchDC= new BranchDC();
            branchDC.branch_code=branchCode;
            branchDC.event_key="search";
            TormundManager.GlobalBranches= BranchesManager.SearchBranches(branchDC);

            if(!TormundManager.GlobalBranches.isEmpty()){


                TormundManager.GlobalCompany= TormundManager.GlobalBranches.get(0).CompanyDC;
                TormundManager.GlobalBranch=TormundManager.GlobalBranches.get(0);
                BranchDC branch=new BranchDC();
                branch.CompanyDC=TormundManager.GlobalCompany;
                TormundManager.GlobalBranches= BranchesManager.SearchBranches(branch);

                ServiceDC service=new ServiceDC();
                service.CompanyDC=TormundManager.GlobalCompany;
                TormundManager.GlobalServices= ServicesServices.SearchServices(service);

                EmployeeDC employeeSearch=new EmployeeDC();
                employeeSearch.branch=TormundManager.GlobalBranch;
                employeeSearch.type_employee=1;
                TormundManager.GlobalBarbers= EmployeesService.SearchEmployees(employeeSearch);

                if(AccessToken.getCurrentAccessToken()!=null && !AccessToken.getCurrentAccessToken().isExpired()){
                    TormundManager.UpdateUserData();
                }else{
                    try {
                        for (int i=0;i<=9000;i++){
                            String hola="";
                            if(hola.isEmpty()){

                            }

                        }
                    }catch (Exception ex){

                    }

                }
            }



        }



        return 0L;
    }

    protected void onProgressUpdate(Integer... progress) {
        String hola="";
    }

    protected void onPostExecute(Long result) {

       /**/

       if(TormundManager.GlobalCompany!=null && TormundManager.GlobalBranch!=null){
           Intent intent= new Intent(context, MainActivity.class);
           context.startActivity(intent);
       }else{
           Intent intent= new Intent(context, BranchCodeActivity.class);
           context.startActivity(intent);
       }

    }
}