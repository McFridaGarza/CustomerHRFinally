package macguffinco.hellrazorbarber.Logic;

import android.util.Log;

import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Comercial.CustomerDC;
import macguffinco.hellrazorbarber.Model.Dates.DateDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.Model.UserDC;
import macguffinco.hellrazorbarber.Services.Dates.DatesService;
import macguffinco.hellrazorbarber.Solution.ApiConnection;

public final class DatesManager {

    public  static DateDC GetCustomerPendingDate(CustomerDC customer){

        if(customer==null) return null;
        if (customer.id<=0) return null;

        DateDC date=new DateDC();
        date.Customer_id=customer.id;
        date.Status=4;


        if(customer.id>0){
            List<DateDC> listDates= DatesService.SearchDate(date);

            if(!listDates.isEmpty()){

                return listDates.get(0);
            }else
                return DatesService.NewPendingDate(date);
        }
       else{
            return null;
        }


    }

    public  static DateDC CreateNewDate(DateDC dateDC){

        if(dateDC==null) return null;


        return DatesService.NewPendingDate(dateDC);


    }

    public static DateDC CreateNewAppointment(DateDC date) {

        final DateDC dateOut=date;

        try {
            String urlAdress="http://"+TormundManager.GlobalServer+"/api/dates";
            URL url = new URL(urlAdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);


            JSONObject jsonParam = new JSONObject();
            jsonParam.put("TormundApp", 1);

            jsonParam.put("sub_status", date.SubStatus);
            jsonParam.put("status", 1);
            jsonParam.put("tormund_app",1);//employee Aplication
            jsonParam.put("status_list", date.status_list);
            jsonParam.put("appointment_date", TormundManager.FormatDateTime(date.AppointmentDate));
            jsonParam.put("due_date",  TormundManager.FormatDateTime(date.DueDate));
            jsonParam.put("event_key","new_pending_date");

            if(date.Branch!=null){
                JSONObject jsonBranch=new JSONObject();
                jsonBranch.put("id",date.Branch.Id);
                jsonParam.put("branch",jsonBranch);
            }

            if(date.Service!=null){
                JSONObject jsonService=new JSONObject();
                jsonService.put("service_id",date.Service.service_id);
                jsonParam.put("Service",jsonService);
            }

            if(date.Employee!=null){
                JSONObject jsonEmployee=new JSONObject();
                jsonEmployee.put("id",date.Employee.id);
                jsonParam.put("Employee",jsonEmployee);
            }

            if(date.Customer!=null){
                JSONObject jsonEmployee=new JSONObject();
                jsonEmployee.put("id",date.Customer.id);
                jsonParam.put("Customer",jsonEmployee);
            }



            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonParam.toString());



            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = ApiConnection.readStream(conn.getInputStream());

                JSONArray array= new JSONArray(responseString);
                if(array.length()>0){
                    DateDC dateDC= new DateDC();
                    JSONObject json =array.getJSONObject(0);
                    //Status 4: Pending
                    if(!json.isNull("id")){
                        dateDC.Id=json.getInt("id");
                    }
                    if(!json.isNull("customer") ){

                        CustomerDC customerDC= new CustomerDC();
                        BranchDC branchDC= new BranchDC();
                        customerDC.id=json.getJSONObject("customer").getInt("id");
                        customerDC.name=json.getJSONObject("customer").getString("name");
                        dateDC.Customer=customerDC;
                        dateDC.tormund_app=json.getInt("tormund_app");

                        if(!json.isNull("branch")){
                            JSONObject jsonBranch=json.getJSONObject("branch");
                            BranchDC branch=new BranchDC();
                            branch.Id=jsonBranch.getInt("id");
                            branch.BranchName=jsonBranch.getString("branchName");
                            dateDC.Branch=branch;
                        }

                        if(!json.isNull("service")){
                            JSONObject jsonBranch=json.getJSONObject("service");
                            ServiceDC serviceDC=new ServiceDC();
                            serviceDC.service_id=jsonBranch.getInt("id");
                            serviceDC.Cost=jsonBranch.getDouble("cost");
                            serviceDC.Price=jsonBranch.getDouble("price");
                            serviceDC.servicetime=jsonBranch.getDouble("servicetime");

                        }

                        if(!json.isNull("employee")){
                            JSONObject jsonBranch=json.getJSONObject("employee");
                            EmployeeDC employeeDC=new EmployeeDC();
                            employeeDC.id=jsonBranch.getInt("id");
                            employeeDC.name=jsonBranch.getString("name");
                            if(!jsonBranch.isNull("repo_files")){

                            }


                        }

                        if(!json.isNull("branch")){
                            JSONObject jsonBranch=json.getJSONObject("branch");
                            BranchDC branch=new BranchDC();
                            branch.Id=jsonBranch.getInt("id");
                            branch.BranchName=jsonBranch.getString("branchName");
                            dateDC.Branch=branch;
                        }


                        return dateDC;
                    }

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
            return null;
        }

        return null;
    }

    public  static DateDC UpdateDate(DateDC dateDC){

        if(dateDC==null) return null;


        return DatesService.UpdateDate(dateDC);


    }

    public  static ArrayList<DateDC> getEnabledDates(DateDC dateDC){

        if(dateDC==null) return null;


        return DatesService.getEnabledDates(dateDC);


    }

}
