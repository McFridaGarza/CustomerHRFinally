package macguffinco.hellrazorbarber.Services.Services;

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
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;

public final class ServicesServices {

    public static ArrayList<ServiceDC> SearchServices(ServiceDC serviceDC)     {

        if(serviceDC==null) return null;
        ArrayList<ServiceDC> servicesList=new ArrayList<ServiceDC>();

        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("id", serviceDC.service_id);
            jsonParam.put("name", serviceDC.Name);
            jsonParam.put("event_key","search");


            JSONObject jsonParamCompany = new JSONObject();
            jsonParamCompany.put("id", serviceDC.CompanyDC.Id);
            jsonParam.put("companydc",jsonParamCompany);

            String urlAdress="http://"+ TormundManager.GlobalServer+"/api/services";
            URL url = new URL(urlAdress);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);


            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(jsonParam.toString());

            os.flush();
            os.close();


            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String responseString = TormundManager.readStream(conn.getInputStream());

                JSONArray array= new JSONArray(responseString);
                if(array.length()>0){
                    for (int i=0 ;i<array.length();i++) {
                        JSONObject json =array.getJSONObject(i);

                        if(json.getString("service_id")!="0"){
                            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                            ServiceDC service= new ServiceDC();
                            service.service_id=json.getInt("service_id");
                            service.Name=json.getString("name");
                            service.CreationDate= TormundManager.JsonStringToDate2(json.getString("creationDate"));
                            service.Price=json.getDouble("price");
                            service.Cost=json.getDouble("cost");
                            service.Price=json.getDouble("price");
                            service.servicetime=json.getDouble("servicetime");

                            /*if(json.getJSONObject("address_Id")!=null){
                                JSONObject jsonService=json.getJSONObject("address_Id");
                                AddressDC address=new  AddressDC(jsonService.getInt("address_Id"),jsonService.getString("street"),jsonService.getString("number"),jsonService.getInt("addressType"),jsonService.getBoolean("isEnabled"),jsonService.getString("googleLocation"));

                                branch.Address_Id=address;

                            }*/


                            servicesList.add(service);

                        }
                    }


                }else {
                    return new ArrayList<ServiceDC>();
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
            return new ArrayList<ServiceDC>();
        }


        return  servicesList;

    }
}
