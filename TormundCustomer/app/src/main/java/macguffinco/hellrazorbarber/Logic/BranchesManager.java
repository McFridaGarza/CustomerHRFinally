package macguffinco.hellrazorbarber.Logic;

import com.facebook.AccessToken;

import java.util.ArrayList;
import java.util.List;

import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Dates.DateDC;
import macguffinco.hellrazorbarber.Services.Branches.BranchesServices;
import macguffinco.hellrazorbarber.Services.Comercial.Customers.CustomerService;
import macguffinco.hellrazorbarber.Services.Dates.DatesService;

public final class BranchesManager {

    public static ArrayList<BranchDC> SearchBranches(BranchDC branchDC){

        try {

            if (branchDC==null)     return new ArrayList<BranchDC>();

            return BranchesServices.SearchBranches(branchDC);


        }catch (Exception e){
          //  LogginCleaner();
        }

          return new ArrayList<BranchDC>();
    }



}
