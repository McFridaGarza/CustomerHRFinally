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

public class RecyclerViewCartAdapter extends RecyclerView.Adapter <RecyclerViewCartAdapter.MyViewHolderCart>{

    private Context mContext;
    private List<Cart> mData;

    public RecyclerViewCartAdapter(Context mContext, List<Cart> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolderCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view=layoutInflater.inflate(R.layout.card_item_cart,parent,false);
        return new MyViewHolderCart(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderCart holder, int position) {
        holder.cartname.setText(mData.get(position).getTitle());
        holder.cartcant.setText(mData.get(position).getCant());
        holder.cartprice.setText(mData.get(position).getPrice());
        holder.imgcart.setImageResource(mData.get(position).getImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolderCart extends RecyclerView.ViewHolder{
        TextView cartname;
        ImageView imgcart;
        TextView cartprice;
        TextView cartcant;
        CardView cardView;


    public MyViewHolderCart(View itemView) {
        super(itemView);
        cartname=itemView.findViewById(R.id.cartname);
        cartprice=itemView.findViewById(R.id.pricecart);
        cartcant=itemView.findViewById(R.id.cartCant);
        imgcart = (ImageView) itemView.findViewById(R.id.cart_img);
        cardView=(CardView) itemView.findViewById(R.id.cardcart);
    }
    }
}





