package macguffinco.hellrazorbarber.Model;

import java.util.Date;

import macguffinco.hellrazorbarber.Model.Comercial.CustomerDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;

public class UserDC
{


    public int Id;
    public String Name;

    public int user_type;

    public String Email;

    public String TokenFacebook;
    public String EmailFacebook;
    public String TokenGoogle;
    public String EmailGoogle;

    public String TokenMacguffin;


    public String password;

    public String fromApplication;

    public String creationDate;

    public String ModifiedDate;

    public UserDC CreatedBy;

    public UserDC ModifiedBy;

    public String gcrecord;

    public String event_key;

    public EmployeeDC employee;

    public CustomerDC customer;

    public byte[] facebook_profile_picture;


}
