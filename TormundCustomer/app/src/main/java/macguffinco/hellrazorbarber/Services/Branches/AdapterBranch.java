package macguffinco.hellrazorbarber.Services.Branches;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import macguffinco.hellrazorbarber.Model.Branches.BranchDC;
import macguffinco.hellrazorbarber.R;

public class AdapterBranch extends RecyclerView.Adapter<AdapterBranch.ViewHolderBranches> {



    public interface OnItemClickListener {
        void onItemClick(BranchDC item);
    }
    private final ArrayList<BranchDC> branchesList;
    private  OnItemClickListener listener;

    public AdapterBranch(ArrayList<BranchDC> branches) {
        this.branchesList = branches;
    }


    public AdapterBranch(ArrayList<BranchDC> branches, OnItemClickListener listener) {
        this.branchesList = branches;
        this.listener = listener;
    }

    @Override
    public ViewHolderBranches onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_branches,null,false);
        return new ViewHolderBranches(view);
    }

    @Override
    public void onBindViewHolder( ViewHolderBranches holder, int position) {
       holder.bind(branchesList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return branchesList.size();
    }

    public static class ViewHolderBranches extends RecyclerView.ViewHolder {

        TextView branchName;
        TextView branchAddress;
        TextView branchPhone;
        ImageView imagePhone;
        ImageView imageMaps;
        TextView branchHorary;
        ImageView imageHorary;

        public ViewHolderBranches(View itemView) {
            super(itemView);
            branchName=(TextView) itemView.findViewById(R.id.branchName);
            branchAddress=(TextView)itemView.findViewById(R.id.branchAddress);
            branchPhone=(TextView)itemView.findViewById(R.id.branchPhone);
            imagePhone=(ImageView)itemView.findViewById(R.id.imagePhone);
            imageMaps=(ImageView)itemView.findViewById(R.id.imageMaps);
            imageHorary=(ImageView)itemView.findViewById(R.id.imageHorary);
            branchHorary=(TextView) itemView.findViewById(R.id.branchHorary);


        }

        public void bind(final BranchDC branchDC, final OnItemClickListener listener) {
            branchName.setText(branchDC.BranchName);
            if(branchDC.Address_Id!=null){
                branchAddress.setText(branchDC.Address_Id.toString());
            }
           if(branchDC.Phone!=null){
               branchPhone.setText(branchDC.Phone);
           }
            if(branchDC.Horary!=null){
                branchHorary.setText(branchDC.Horary);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(branchDC   );
                }
            });
        }

    }
}

