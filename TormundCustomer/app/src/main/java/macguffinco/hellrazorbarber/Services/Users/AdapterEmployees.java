package macguffinco.hellrazorbarber.Services.Users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.R;
import macguffinco.hellrazorbarber.Services.Branches.AdapterBranch;
import macguffinco.hellrazorbarber.Services.Services.AdapterServices;

public class AdapterEmployees extends RecyclerView.Adapter<AdapterEmployees.ViewHolderBarbers> {


    public interface OnItemClickListener {
        void onItemClick(EmployeeDC item);
    }
    private final ArrayList<EmployeeDC> barbersList;
    private AdapterEmployees.OnItemClickListener listener;

    public AdapterEmployees(ArrayList<EmployeeDC> barbers) {
        this.barbersList = barbers;
    }


    public AdapterEmployees(ArrayList<EmployeeDC> barbersDC, AdapterEmployees.OnItemClickListener listener) {
        this.barbersList = barbersDC;
        this.listener = listener;
    }

    @Override
    public AdapterEmployees.ViewHolderBarbers onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_employees,parent,false);
        return new AdapterEmployees.ViewHolderBarbers(view);
    }

    @Override
    public void onBindViewHolder(AdapterEmployees.ViewHolderBarbers holder, int position) {
        holder.bind(barbersList.get(position),listener);
    }

    @Override
    public int getItemCount() {

        return barbersList.size();
    }

    public static class ViewHolderBarbers extends RecyclerView.ViewHolder {

        TextView BarberName;
        ImageView imageBarber;

        public ViewHolderBarbers(View itemView) {
            super(itemView);
            BarberName=(TextView) itemView.findViewById(R.id.BarberName);
            imageBarber= (ImageView) itemView.findViewById(R.id.imageBarber);
        }

        public void bind(final EmployeeDC barberDC, final AdapterEmployees.OnItemClickListener listener) {
            BarberName.setText(barberDC.name);
        if(barberDC.vault_file_id!=null){
            Picasso.with(itemView.getContext()).load(barberDC.vault_file_id.Url).into(imageBarber);
        }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(barberDC   );
                }
            });
        }

    }
}
