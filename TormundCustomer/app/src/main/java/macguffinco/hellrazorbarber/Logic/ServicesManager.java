package macguffinco.hellrazorbarber.Logic;

import java.util.ArrayList;

import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.Services.Branches.BranchesServices;
import macguffinco.hellrazorbarber.Services.Services.ServicesServices;

public final class ServicesManager {

    public static ArrayList<ServiceDC> SearchServices(ServiceDC serviceDC){

        try {

            if (serviceDC==null)     return new ArrayList<ServiceDC>();

            return ServicesServices.SearchServices(serviceDC);


        }catch (Exception e){
            //  LogginCleaner();
        }

        return new ArrayList<ServiceDC>();
    }

}
