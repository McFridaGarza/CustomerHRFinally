package macguffinco.hellrazorbarber.Services.Users;

import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.json.*;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Comercial.CustomerDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.UserDC;

public final class UserService {

    public static UserDC NewUser(UserDC user) {

       final UserDC usuario=user;

        try {
            String urlAdress="http://"+TormundManager.GlobalServer+"/api/users";
            URL url = new URL(urlAdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("name", usuario.Name);
            jsonParam.put("token_facebook", usuario.TokenFacebook);
            jsonParam.put("email_facebook",usuario.EmailFacebook);
            jsonParam.put("token_google",usuario.TokenGoogle);
            jsonParam.put("email_google",usuario.EmailGoogle);
            jsonParam.put("email",usuario.Email);

            if(usuario.customer!=null){
                JSONObject jsonCustomer= new JSONObject();
                if(usuario.customer.name!=null && !usuario.customer.name.equals("")){
                    jsonCustomer.put("name",usuario.customer.name);
                }
                if(usuario.customer.principal_email!=null && !usuario.customer.principal_email.equals("")){
                    jsonCustomer.put("principal_email",usuario.customer.principal_email);
                }
                if(usuario.customer.born_date!=null && !usuario.customer.born_date.equals("")){
                    jsonCustomer.put("born_date",TormundManager.FormatDateTime(usuario.customer.born_date));
                }


                if(usuario.customer.branch!=null){
                    JSONObject jsonBranch= new JSONObject();
                    jsonBranch.put("id",usuario.customer.branch.Id);
                    jsonCustomer.put("branch",jsonBranch);
                }
                jsonParam.put("user_type",2); //Customer
                jsonParam.put("customer",jsonCustomer);

            }

            if(usuario.employee!=null){
                JSONObject jsonEmployee= new JSONObject();
                if(usuario.employee.name!=null && !usuario.employee.name.equals("")){
                    jsonEmployee.put("name",usuario.employee.name);
                }
                if(usuario.employee.born_date!=null && !usuario.employee.born_date.equals("")){
                    jsonEmployee.put("born_date",usuario.employee.born_date);
                }
                if(usuario.employee.hire_date!=null && !usuario.employee.hire_date.equals("")){
                    jsonEmployee.put("hire_date",usuario.employee.hire_date);
                }
                jsonEmployee.put("type_employee",usuario.employee.type_employee);


                if(usuario.employee.branch!=null){
                    JSONObject jsonBranch= new JSONObject();
                    jsonBranch.put("id",usuario.employee.branch.Id);
                    jsonEmployee.put("branch",jsonBranch);
                }
                jsonParam.put("user_type",1); //Employee
                jsonParam.put("employee",jsonEmployee);

            }

            jsonParam.put("from_application","HellRazorMobile");


            jsonParam.put("event_key","new");



            Log.i("JSON", jsonParam.toString());
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = TormundManager.readStream(conn.getInputStream());

                JSONArray array= new JSONArray(responseString);
                if(array.length()>0){

                    JSONObject json =array.getJSONObject(0);
                    if(!json.isNull("id")){
                        UserDC userDc= new UserDC();
                        userDc.TokenFacebook=json.getString("token_facebook");
                        userDc.EmailFacebook=json.getString("email_facebook");
                        userDc.TokenGoogle=json.getString("token_google");
                        userDc.EmailGoogle=json.getString("email_google");
                        userDc.Email=json.getString("email");
                        userDc.user_type=json.getInt("user_type");

                        if(!json.isNull("customer")){
                            JSONObject jsonCustomer=json.getJSONObject("customer");
                            CustomerDC customerDC= new CustomerDC();
                            customerDC.id=jsonCustomer.getInt("id");
                            customerDC.name=jsonCustomer.getString("name");
                            userDc.customer=customerDC;
                        }
                        if(!json.isNull("employee")){
                            JSONObject jsonEmployee=json.getJSONObject("employee");
                            EmployeeDC employeeDC= new EmployeeDC();
                            employeeDC.id=jsonEmployee.getInt("id");
                            employeeDC.name=jsonEmployee.getString("name");
                            userDc.employee=employeeDC;
                        }

                        //userCreated.facebook_user_id=userDc.facebook_user_id;
                        return userDc;
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
        }


        return user;
    }

    public static UserDC SearchUser(UserDC user) {

        if(user==null) return null;

        final UserDC userIn=user;
        final UserDC userOut=new UserDC();





                try {
                    String urlAdress="http://"+TormundManager.GlobalServer+"/api/users";
                    URL url = new URL(urlAdress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("event_key", "search");
                    jsonParam.put("token_facebook", userIn.TokenFacebook);
                    jsonParam.put("email_facebook",userIn.EmailFacebook);
                    jsonParam.put("token_google",userIn.TokenGoogle);
                    jsonParam.put("email_google",userIn.EmailGoogle);
                    jsonParam.put("name",userIn.Name);
                    jsonParam.put("from_application","HellRazorMobile");


                    jsonParam.put("event_key","search");



                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    String responseMessage = conn.getResponseMessage();

                    if(responseCode == HttpURLConnection.HTTP_OK){
                        String responseString = TormundManager.readStream(conn.getInputStream());

                        JSONArray array= new JSONArray(responseString);
                        if(array.length()>0){
                            JSONObject json =array.getJSONObject(0);

                            if(!json.isNull("id")){
                                UserDC userDc= new UserDC();
                                userDc.Id=json.getInt("id");
                                userDc.Name=json.getString("name");
                                userDc.user_type=json.getInt("user_type");
                                if(!json.isNull("user_type")){
                                    userDc.user_type=json.getInt("user_type");
                                }

                                if(!json.isNull("token_facebook")){
                                    userDc.TokenFacebook=json.getString("token_facebook");
                                }
                                if(!json.isNull("email_facebook")){
                                    userDc.EmailFacebook=json.getString("email_facebook");
                                }
                                if(!json.isNull("token_google")){
                                    userDc.TokenGoogle=json.getString("token_google");
                                }
                                if(!json.isNull("token_macguffin")){
                                    userDc.TokenMacguffin=json.getString("token_macguffin");
                                }
                                if(!json.isNull("email_google")){
                                    userDc.EmailGoogle=json.getString("email_google");
                                }
                                if(!json.isNull("creationdate")){
                                    userDc.creationDate= json.getString("creationdate");
                                }
                                if(!json.isNull("fromApplication")){
                                    userDc.fromApplication=json.getString("from_application");
                                }
                                if(!json.isNull("facebook_profile_picture")){
                                    //userDc.facebook_profile_picture=json.getJSONArray("facebook_profile_picture");
                                }

                                if(!json.isNull("customer")){
                                    JSONObject jsonCustomer= json.getJSONObject("customer");
                                    CustomerDC customerDC=new CustomerDC();
                                    customerDC.id=jsonCustomer.getInt("id");
                                    customerDC.name=jsonCustomer.getString("name");
                                    customerDC.principal_email=jsonCustomer.getString("principal_email");
                                    userDc.customer=customerDC;
                                }
                                if(!json.isNull("employees")){
                                    JSONObject jsonEmployee= json.getJSONObject("employees");
                                    EmployeeDC employeeDC=new EmployeeDC();
                                    employeeDC.id=jsonEmployee.getInt("id");
                                    employeeDC.name=jsonEmployee.getString("name");
                                    employeeDC.type_employee=jsonEmployee.getInt("type_employee");
                                    userDc.employee=employeeDC;
                                }



                                return  userDc;
                                //userOut.facebook_user_id=userDc.facebook_user_id;
                            }

                        }else {
                            return null;
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
/*
        }});

        thread.start();*/

        return  user;

    }

}
