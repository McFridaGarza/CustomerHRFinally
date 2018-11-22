package macguffinco.hellrazorbarber;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class cartActivity extends AppCompatActivity {
    List<Cart> lstCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        lstCart=new ArrayList<>();
        lstCart.add(new Cart("Original Pomade","$200","1",R.drawable.ponteduro));
        lstCart.add(new Cart("Matte Pomade","$250","1",R.drawable.ponteduro1));
        lstCart.add(new Cart("Shampoo","$160","1",R.drawable.ponteduro5));

        RecyclerView myrv =(RecyclerView) findViewById(R.id.recycler_cart);
        RecyclerViewCartAdapter myAdapter = new RecyclerViewCartAdapter(this,lstCart);
        myrv.setLayoutManager(new GridLayoutManager(this,1));
        myrv.setAdapter(myAdapter);





    }
}
