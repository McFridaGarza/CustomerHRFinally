package macguffinco.hellrazorbarber.Services.Services;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.Model.Services.ServiceDC;
import macguffinco.hellrazorbarber.R;
import macguffinco.hellrazorbarber.Services.Branches.AdapterBranch;

public class AdapterServices extends RecyclerView.Adapter<AdapterServices.ViewHolderServices> {


    public interface OnItemClickListener {
        void onItemClick(ServiceDC item);
    }
    private final ArrayList<ServiceDC> servicesList;
    private AdapterServices.OnItemClickListener listener;

    public AdapterServices(ArrayList<ServiceDC> services) {
        this.servicesList = services;
    }


    public AdapterServices(ArrayList<ServiceDC> servicesDC, AdapterServices.OnItemClickListener listener) {
        this.servicesList = servicesDC;
        this.listener = listener;
    }

    @Override
    public AdapterServices.ViewHolderServices onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_services,parent,false);
        return new AdapterServices.ViewHolderServices(view);
    }

    @Override
    public void onBindViewHolder(AdapterServices.ViewHolderServices holder, int position) {
        holder.bind(servicesList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public static class ViewHolderServices extends RecyclerView.ViewHolder {

        TextView ServiceName;


        public ViewHolderServices(View itemView) {
            super(itemView);
            ServiceName=(TextView) itemView.findViewById(R.id.ServiceName);




        }

        public void bind(final ServiceDC serviceDC, final AdapterServices.OnItemClickListener listener) {
            ServiceName.setText(serviceDC.Name);



            // Picasso.with(itemView.getContext()).load(branchDC.VaultFileDC.Url).into(foto);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(serviceDC   );
                }
            });
        }

    }
}
