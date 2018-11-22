package macguffinco.hellrazorbarber.Model.Comercial;

import java.util.ArrayList;
import java.util.Date;

import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.CommentDC;
import macguffinco.hellrazorbarber.Model.UserDC;
import macguffinco.hellrazorbarber.Model.VaultFiles.VaultFileDC;

public class CustomerDC {

    public int id;
    public String name;
    public BranchDC branch;
    public UserDC user;
    public String event_key;
    public String principalPhone;
    public String principal_email;
    public int user_id;
    public Date born_date;
    public Date creationDate;
    public String ultimacita;
    public int cantidadCitas;
    public byte[] photoBytes;
    public String photoName;
    public String photo_string64;
    public ArrayList<VaultFileDC> repo_files;
    public ArrayList<CommentDC> comments;

}
