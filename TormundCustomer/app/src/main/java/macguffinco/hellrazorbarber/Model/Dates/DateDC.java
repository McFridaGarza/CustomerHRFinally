package macguffinco.hellrazorbarber.Model.Dates;


import java.util.Date;

import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Comercial.CustomerDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.Model.UserDC;

public class DateDC
{
    public int Id ;
    public String Description ;
    public BranchDC Branch ;
    public ServiceDC Service ;
    public EmployeeDC Employee ;
    public int Employee_id;
    public CustomerDC Customer ;
    public Date AppointmentDate ;
    public int Customer_id;
    public Date DueDate ;
    public Date CreationDate ;
    public Date LastModified ;
    public UserDC CreatedBy ;
    public UserDC ModifiedBy ;
    public byte[] Photo ;
    public int Status ;
    public String status_list;
    public int SubStatus ;
    public String FromApp;
    public String event_key;
    public int tormund_app ;


}