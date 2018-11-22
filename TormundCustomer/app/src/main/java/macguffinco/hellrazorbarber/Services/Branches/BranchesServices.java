package macguffinco.hellrazorbarber.Services.Branches;

import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Branches.AddressDC;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Branches.BranchPreferenceDC;
import macguffinco.hellrazorbarber.Model.Branches.CompanyDC;
import macguffinco.hellrazorbarber.Model.Branches.HoraryDC;
import macguffinco.hellrazorbarber.Model.Dates.DateDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.Model.VaultFiles.VaultFileDC;

public final class BranchesServices {

    public static ArrayList<BranchDC> SearchBranches(BranchDC branchDC)     {

        if(branchDC==null) return null;
        ArrayList<BranchDC> branchesList=new ArrayList<BranchDC>();

        try {
            JSONObject jsonParam = new JSONObject();
            if(branchDC.Id>0){
                jsonParam.put("id", branchDC.Id);
            }
            if(branchDC.BranchName!=null && !branchDC.BranchName.equals("")){
                jsonParam.put("branchname", branchDC.BranchName);
            }
            if(branchDC.branch_code!=null && !branchDC.branch_code.equals("")){
                jsonParam.put("branch_code", branchDC.branch_code);
            }

            jsonParam.put("event_key","search");


            if(branchDC.CompanyDC!=null){

                JSONObject jsonParamCompany = new JSONObject();
                jsonParamCompany.put("id", branchDC.CompanyDC.Id);
                jsonParam.put("companydc",jsonParamCompany);

            }


            String urlAdress="http://"+TormundManager.GlobalServer+"/api/branches";
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

                        if(json.getString("id")!="0"){
                            DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
                            BranchDC branch= new BranchDC();
                            branch.Id=json.getInt("id");
                            branch.Email=json.getString("email");
                            branch.BranchName= json.getString("branchName");
                            branch.foto1=2131165326;
                            branch.Phone= json.getString("principalPhone");
                            branch.Horary=json.getString("horary");

                            branch.CreationDate= TormundManager.JsonStringToDate2(json.getString("creationDate"));

                            if(!json.isNull("address_Id")){
                                JSONObject jsonService=json.getJSONObject("address_Id");
                                AddressDC address=new  AddressDC(jsonService.getInt("address_Id"),jsonService.getString("street"),jsonService.getString("number"),jsonService.getInt("addressType"),jsonService.getBoolean("isEnabled"),jsonService.getString("googleLocation"));

                                branch.Address_Id=address;

                            }

                            if(!json.isNull("companyDC")){
                                JSONObject jsonCompany=json.getJSONObject("companyDC");
                                CompanyDC companyDC=new CompanyDC();
                                companyDC.Id=jsonCompany.getInt("id");
                                companyDC.Name=jsonCompany.getString("name");
                                companyDC.Email=jsonCompany.getString("email");

                                branch.CompanyDC=companyDC;
                            }

                            if(!json.isNull("preference")){
                                JSONObject jsonPreference=json.getJSONObject("preference");
                                BranchPreferenceDC preferenceDC=new BranchPreferenceDC();
                                preferenceDC.id=jsonPreference.getInt("id");
                                preferenceDC.description=jsonPreference.getString("description");
                                preferenceDC.time_allowed_between_appointment=jsonPreference.getInt("time_allowed_between_appointment");
                                if(!jsonPreference.isNull("horaries")){
                                    JSONArray array_horaries= jsonPreference.getJSONArray("horaries");
                                    preferenceDC.horaries= new ArrayList<HoraryDC>();

                                    for(int h=0;h<array_horaries.length();h++){
                                        JSONObject jsonHorary=array_horaries.getJSONObject(h);
                                        HoraryDC horaryDC= new HoraryDC();
                                        horaryDC.id=jsonHorary.getInt("id");
                                        horaryDC.day_of_week=jsonHorary.getInt("day_of_week");
                                        horaryDC.horary_type=jsonHorary.getInt("horary_type");
                                        horaryDC.start=jsonHorary.getString("start");
                                        horaryDC.finish=jsonHorary.getString("finish");
                                        horaryDC.description=jsonHorary.getString("description");

                                        preferenceDC.horaries.add(horaryDC);
                                    }

                                }


                                branch.preference=preferenceDC;
                            }


                            if(!json.isNull("vaultFileDC")){
                                JSONObject jsonVault=json.getJSONObject("vaultFileDC");
                                VaultFileDC vault=new VaultFileDC();
                                vault.Id=jsonVault.getInt("id");
                                vault.Url=jsonVault.getString("url");
                                branch.VaultFileDC=vault;
                            }


                            if(!json.isNull("repo_files")){
                                branch.repo_files=new ArrayList<>();
                                JSONArray files=json.getJSONArray("repo_files");
                                for(int f=0;f<files.length() ;f++){
                                    JSONObject jsonObjPicture=files.getJSONObject(f);
                                    VaultFileDC vault= new VaultFileDC();
                                    vault.Id=jsonObjPicture.getInt("id");
                                    vault.Description=jsonObjPicture.getString("description");
                                    if(!jsonObjPicture.isNull("type_vault_file")){
                                        vault.type_vault_file=jsonObjPicture.getInt("type_vault_file");
                                    }

                                    if(!jsonObjPicture.isNull("url")){
                                        vault.Url=jsonObjPicture.getString("url");
                                    }

                                    branch.repo_files.add(vault);
                                }
                            }







                            branchesList.add(branch);

                        }
                    }


                }else {
                    return new ArrayList<BranchDC>();
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
            return new ArrayList<BranchDC>();
        }


        return  branchesList;

    }

}
