package macguffinco.hellrazorbarber.Services.Users;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Branches.AddressDC;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Branches.CompanyDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.Model.UserDC;
import macguffinco.hellrazorbarber.Model.VaultFiles.VaultFileDC;
import macguffinco.hellrazorbarber.Solution.ApiConnection;

public class EmployeesService {

    public static ArrayList<EmployeeDC> SearchEmployees(EmployeeDC  employeeDC)     {

        if(employeeDC==null) return null;
        ArrayList<EmployeeDC> employees=new ArrayList<EmployeeDC>();

        try {
            JSONObject jsonEmployee=new JSONObject();
            jsonEmployee.put("event_key","search");
            if(employeeDC.user!=null ){

                if(employeeDC.user.Id>0){
                    JSONObject jsonUser= new JSONObject();
                    jsonUser.put("id",employeeDC.user.Id);
                    jsonEmployee.put("user",jsonUser);

                }

            }

            if(employeeDC.branch!=null ){


                JSONObject jsonBranch= new JSONObject();
                jsonBranch.put("id",employeeDC.branch.Id);
                jsonBranch.put("branchname",employeeDC.branch.BranchName);
                jsonEmployee.put("branch",jsonBranch);


            }
            if(employeeDC.type_employee>0){
                jsonEmployee.put("type_employee",employeeDC.type_employee);
            }

            HttpURLConnection conn= ApiConnection.getConnection("employees");
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonEmployee.toString());

            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = ApiConnection.readStream(conn.getInputStream());

                JSONArray array= new JSONArray(responseString);
                int top =10;
                if(array.length()>=top){
                    top=10;
                }else{
                    top=array.length();
                }
                if(array.length()>0){
                    for (int i=0 ;i<top;i++) {
                        JSONObject json =array.getJSONObject(i);

                        if(json.getString("id")!="0"){

                            EmployeeDC employeeReturn= new EmployeeDC();
                            employeeReturn.id=json.getInt("id");
                            employeeReturn.name=json.getString("name");

                            if(!json.isNull("branch")){
                                JSONObject jsonBranch=json.getJSONObject("branch");
                                BranchDC branch=new BranchDC();
                                branch.Id=jsonBranch.getInt("id");
                                branch.BranchName=jsonBranch.getString("branchName");
                                employeeReturn.branch=branch;
                            }


                            if(!json.isNull("repo_files")){
                                employeeReturn.repo_files=new ArrayList<>();
                                JSONArray files=json.getJSONArray("repo_files");
                                for(int k=0;k<files.length() ;k++){
                                    JSONObject jsonObjPicture=files.getJSONObject(k);
                                    VaultFileDC vault= new VaultFileDC();
                                    vault.Id=jsonObjPicture.getInt("id");
                                    //vault.Description=jsonObjPicture.getString("description");
                                    if(!jsonObjPicture.isNull("url")){
                                        vault.Url=jsonObjPicture.getString("url");
                                    }

                                    employeeReturn.repo_files.add(vault);
                                }
                            }





                            employees.add(employeeReturn);

                        }
                    }


                }else {
                    return new ArrayList<EmployeeDC>();
                }



            }else{
                Log.v("CatalogClient", "Response code:"+ responseCode);
                Log.v("CatalogClient", "Response message:"+ responseMessage);
            }
            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG" , conn.getResponseMessage());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<EmployeeDC>();
        }


        return  employees;


    }

}
