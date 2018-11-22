package macguffinco.hellrazorbarber.Activities.Welcome;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import macguffinco.hellrazorbarber.Logic.BranchesManager;
import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Branches.CompanyDC;
import macguffinco.hellrazorbarber.R;

public class BranchCodeActivity extends AppCompatActivity {
 Button Continuar;
 EditText edit1;
 EditText edit2;
 EditText edit3;
 EditText edit4;
 String texto1="";
 String texto2="";
 String texto3="";
 String texto4="";
 String Code="";
 BranchCodeActivity _branchCodeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_macguffin__code_);

        _branchCodeActivity=this;
        edit1=(EditText)findViewById(R.id.edit1);
        edit2=(EditText)findViewById(R.id.edit2);
        edit3=(EditText)findViewById(R.id.edit3);
        edit4=(EditText)findViewById(R.id.edit4);
        Continuar=(Button)findViewById(R.id.btn_continuar);

        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {

                if(edit1.getText().toString().equals("")) return;

                if(ValidateIsNumber(edit1.getText().toString())){
                    texto1= edit1.getText().toString();
                    edit2.requestFocus();
                }else{
                    texto1="";
                    edit1.setText("");
                    return;
                }


            }
        });

       edit2.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               texto2= edit2.getText().toString();

           }

           @Override
           public void afterTextChanged(Editable s) {
               if(edit2.getText().toString().equals("")) return;

               if(ValidateIsNumber(edit2.getText().toString())){
                   texto2= edit2.getText().toString();
                   edit3.requestFocus();
               }else{
                   texto2="";
                   edit2.setText("");
                   return;
               }
           }
       });


       edit3.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               texto3= edit3.getText().toString();

           }

           @Override
           public void afterTextChanged(Editable s) {
               if(edit3.getText().toString().equals("")) return;

               if(ValidateIsNumber(edit3.getText().toString())){
                   texto3= edit3.getText().toString();
                   edit4.requestFocus();
               }else{
                   texto3="";
                   edit3.setText("");
                   return;
               }
           }
       });

       edit4.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {


           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               texto4= edit4.getText().toString();
           }

           @Override
           public void afterTextChanged(Editable s) {

               if(edit4.getText().toString().equals("")) return;

               if(ValidateIsNumber(edit4.getText().toString())){
                   texto4= edit4.getText().toString();
                   Continuar.performClick();
               }else{
                   texto4="";
                   edit4.setText("");
                   return;
               }


           }
       });
               Continuar.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Code=texto1+texto2+texto3+texto4;

                new BranchTask(_branchCodeActivity).execute();


                   }
               });

    }


    public void ClearBranchCode(){

        SharedPreferences preferences= getSharedPreferences("TormundStylist", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("BranchCode");
        editor.clear();
        editor.commit();

    }




    public boolean ValidateIsNumber(String value){

        if(value.length()==1 && value.matches("[0-9]")){
            return true;
        }
        return false;
    }

}


 class  BranchTask extends AsyncTask<String, Integer, Long> {

private BranchCodeActivity _activity;

public BranchTask(BranchCodeActivity branchCodeActivity){

    _activity=branchCodeActivity;
}

    @Override
    protected Long doInBackground(String... strings) {
        if (_activity.Code.length() == 4) {


            BranchDC branchDC= new BranchDC();
            branchDC.branch_code=_activity.Code;
            branchDC.event_key="search";
            TormundManager.GlobalBranches= BranchesManager.SearchBranches(branchDC);





        } else {
            _activity.ClearBranchCode();
            _activity.edit1.setText("");
            _activity.edit2.setText("");
            _activity.edit3.setText("");
            _activity.edit4.setText("");
            _activity.edit1.requestFocus();
            Toast.makeText(_activity, "Código Incorrecto", Toast.LENGTH_SHORT).show();
        }

        return 0L;
    }

    @Override
    protected void onPostExecute(Long result) {

        if(!TormundManager.GlobalBranches.isEmpty()){

            SharedPreferences preferences= _activity.getSharedPreferences("TormundStylist", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("BranchCode",_activity.Code.toString());
            editor.commit();
            TormundManager.goMainRun(_activity.getApplicationContext());
        }else{
            _activity.ClearBranchCode();
            _activity.edit1.setText("");
            _activity.edit2.setText("");
            _activity.edit3.setText("");
            _activity.edit4.setText("");
            _activity.edit1.requestFocus();
            Toast.makeText(_activity, "Código Incorrecto", Toast.LENGTH_SHORT).show();
        }

    }
}


