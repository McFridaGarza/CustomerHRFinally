package macguffinco.hellrazorbarber.Model;

import java.util.Date;

import macguffinco.hellrazorbarber.Model.Comercial.CustomerDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;

public class CommentDC
{



    public int id ;

    public String title ;

    public String message ;

    public CustomerDC customer ;

    public EmployeeDC employee ;

    public int comment_type ;

    public Date creation_date ;





}
