package macguffinco.hellrazorbarber;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewMarketAdapter extends RecyclerView.Adapter <RecyclerViewMarketAdapter.MyViewHolter> {

private Context mContext;
private List<Market> mData;



    public RecyclerViewMarketAdapter(Context mContext, List<Market> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View view;

          LayoutInflater layoutInflater = LayoutInflater.from(mContext);
          view=layoutInflater.inflate(R.layout.cardview_item_market,parent,false);
           return new MyViewHolter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolter holder, final int position) {
    holder.tittlemarket.setText(mData.get(position).getTitle());
    holder.imgmarket.setImageResource(mData.get(position).getImage());
    holder.cardView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(mContext,DescriptionActivity.class);
            intent.putExtra("Titulo",mData.get(position).getTitle());
            intent.putExtra("Descripcion",mData.get(position).getDescription());
            intent.putExtra("Precio",mData.get(position).getPrice());
            intent.putExtra("Imagen",mData.get(position).getImage());
            mContext.startActivity(intent);
        }
    });
    //set click

    }

    @Override
    public int getItemCount()
    {
        return mData.size();
    }

    public static class  MyViewHolter extends RecyclerView.ViewHolder{
       TextView tittlemarket;
       ImageView imgmarket;
       CardView cardView;


        public MyViewHolter(View itemView) {
            super(itemView);

            tittlemarket=itemView.findViewById(R.id.titulomarket);
            imgmarket = (ImageView) itemView.findViewById(R.id.marketimg);
            cardView=(CardView) itemView.findViewById(R.id.cardviewMarket);
        }
    }

}
