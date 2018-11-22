package macguffinco.hellrazorbarber.Model.Branches;

import java.util.ArrayList;
import java.util.Date;

import macguffinco.hellrazorbarber.Model.VaultFiles.VaultFileDC;

public class BranchDC {

    public BranchDC(String Name, String Email, int foto1,String phone,AddressDC address) {
        this.BranchName = Name;
        this.Email = Email;
        this.foto1 = foto1;
        this.Phone=phone;
        this.Address_Id= address;

    }

    public BranchDC() {

    }

    public int Id ;

    public String BranchName ;

    public String branch_code;

    public String event_key;


    public String Email ;
    public String Phone ;

    public Date CreationDate ;

    public byte[] Photo ;

    public VaultFileDC VaultFileDC;

    public CompanyDC CompanyDC;

    public AddressDC Address_Id;

    public BranchPreferenceDC preference;

    public int foto1;

    public String Horary;

    public ArrayList<VaultFileDC> repo_files;

}
