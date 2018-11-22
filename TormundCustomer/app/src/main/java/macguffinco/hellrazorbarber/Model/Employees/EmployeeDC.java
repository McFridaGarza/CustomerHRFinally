package macguffinco.hellrazorbarber.Model.Employees;

import java.util.ArrayList;
import java.util.Date;

import macguffinco.hellrazorbarber.Model.Branches.AddressDC;
import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Branches.CompanyDC;
import macguffinco.hellrazorbarber.Model.UserDC;
import macguffinco.hellrazorbarber.Model.VaultFiles.VaultFileDC;

public class EmployeeDC {

    public int id;
    public String name;
    public BranchDC branch;
    public AddressDC Address_id;
    public String event_key;
    public String principal_phone;
    public String born_date;
    public String hire_date;
    public Date creation_date;
    public VaultFileDC vault_file_id;
    public CompanyDC CompanyDC;
    public UserDC user;
    public ArrayList<VaultFileDC> repo_files;
    public String barber_round_picture_url;
    public int type_employee;

}
