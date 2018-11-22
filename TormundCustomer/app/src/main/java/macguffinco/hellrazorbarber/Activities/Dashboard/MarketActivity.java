package macguffinco.hellrazorbarber.Activities.Dashboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//import macguffinco.hellrazorbarber.Activities.Common.BarberListActivityTask;
import macguffinco.hellrazorbarber.Activities.Dates.DatesBrunchActivity;
import macguffinco.hellrazorbarber.DescriptionActivity;
import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Market;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.Model.Market.ProductDC;
import macguffinco.hellrazorbarber.R;
import macguffinco.hellrazorbarber.RecyclerViewMarketAdapter;
import macguffinco.hellrazorbarber.cartActivity;

public class MarketActivity extends AppCompatActivity {
List <Market> lstMarket;
    private Context mContext;

    public ImageView imageView = null;
    public ImageView imgHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);



        lstMarket=new ArrayList<>();
        lstMarket.add(new Market("Original Pomade","$200","Pomada realizada a base de agua con un acabado de alto brillo y con firmeza alta para peinados exigentes, su fragancia combina el Bay rum, hoja de tabaco y mezclas de vainilla y naranja.",R.drawable.ponteduro));
        lstMarket.add(new Market("cabello","$250","Nuestro aceite para el cuidado de la barba aporta brillo, suavidad y un ligero control sobre el pelo facial; hecho de aceite de argan, aceite de coco orgánico, aceite de jojoba, aceite de almendra y con una frangancia de aceite esencial de pino.\n" +
                "\n" +
                "Ingredientes 100% naturales!",R.drawable.ponteduro1));
        lstMarket.add(new Market("Beard Balm","$160","El beard balm es un producto que hidrata , suaviza y ayuda a estilo de su barba. Hecho de ingredientes 100% naturales tal como bees wax, shea butter, Cocoa butter, argan oil, jojoba oil, sweet almond oil & coconut oil y con un aroma a butter mint. Estos ingredientes tienen por objeto facilitar el aseo adecuado de la barba",R.drawable.ponteduro3));
        lstMarket.add(new Market("Shampoo Ponteduro","$160","El shampoo de batamote es elaborado con los más finos ingredientes, adicionado con extractos de hierbas naturales tales como el batamote (Baccharis Glutinosae), Sábila (Aloe Vera), para estimular y reforzar el crecimiento normal del cabello y prevenir su caida prematura. La Sábila le refuerza el poder suavizante y limpiador. El batamote nutre y restaura el brillo del cabello. El aroma y el color son aportados de manera natural por los extractos de las hierbas.",R.drawable.ponteduro4));
        lstMarket.add(new Market("Matte Pomade","$220","Nuestra pomada matte da un aspecto natural al cabello gracias a la mezcla de arcilla y cera de abejas, ademas de ser de base de agua tiene una fijación firme y es maleable, con ella se puede dar estilos tradicionales y modernos con una apariencia y sensación natural, contando con una fragancia de coco y hoja de tabaco.",R.drawable.ponteduro5));
        lstMarket.add(new Market("Matte Pomade","$220","Nuestra pomada matte da un aspecto natural al cabello gracias a la mezcla de arcilla y cera de abejas, ademas de ser de base de agua tiene una fijación firme y es maleable, con ella se puede dar estilos tradicionales y modernos con una apariencia y sensación natural, contando con una fragancia de coco y hoja de tabaco.",R.drawable.ponteduro5));
        lstMarket.add(new Market("Original Pomade","$200","Pomada realizada a base de agua con un acabado de alto brillo y con firmeza alta para peinados exigentes, su fragancia combina el Bay rum, hoja de tabaco y mezclas de vainilla y naranja.",R.drawable.ponteduro));
        lstMarket.add(new Market("Beard Balm","$160","El beard balm es un producto que hidrata , suaviza y ayuda a estilo de su barba. Hecho de ingredientes 100% naturales tal como bees wax, shea butter, Cocoa butter, argan oil, jojoba oil, sweet almond oil & coconut oil y con un aroma a butter mint. Estos ingredientes tienen por objeto facilitar el aseo adecuado de la barba",R.drawable.ponteduro3));
        lstMarket.add(new Market("Shampoo Ponteduro","$160","El shampoo de batamote es elaborado con los más finos ingredientes, adicionado con extractos de hierbas naturales tales como el batamote (Baccharis Glutinosae), Sábila (Aloe Vera), para estimular y reforzar el crecimiento normal del cabello y prevenir su caida prematura. La Sábila le refuerza el poder suavizante y limpiador. El batamote nutre y restaura el brillo del cabello. El aroma y el color son aportados de manera natural por los extractos de las hierbas.",R.drawable.ponteduro4));
        lstMarket.add(new Market("Beard Oil","$180","Nuestro aceite para el cuidado de la barba aporta brillo, suavidad y un ligero control sobre el pelo facial; hecho de aceite de argan, aceite de coco orgánico, aceite de jojoba, aceite de almendra y con una frangancia de aceite esencial de pino.\n" +
                "\n" +
                "Ingredientes 100% naturales!",R.drawable.ponteduro1));

        RecyclerView myrv =(RecyclerView) findViewById(R.id.recyclerMarketList);
        RecyclerViewMarketAdapter myAdapter = new RecyclerViewMarketAdapter(this,lstMarket);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);


        imageView= findViewById(R.id.imgcarrito);
        final Intent intent= new Intent(this, cartActivity.class);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(intent);


            }
        });

        imgHeader=findViewById(R.id.imgHeaderProducts);
        Picasso.with(this)
                .load(TormundManager.GlobalBranch.VaultFileDC.Url)
                .fit()
                .into(imgHeader);



        /*RECYCLER VIEW
        recyclermarket=(RecyclerView)findViewById(R.id.recyclerBarberList);
        recyclermarket.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ProductDC> productList=new ArrayList<ProductDC>();
        ProductDC product= new ProductDC();
        product.name ="Crema para peinar";
        product.cost= 200;
        product.price=250;
        product.description="suave";
        product.url="https://images.eltiempo.digital/files/article_main/uploads/2017/12/14/5a3305fcc2ecd.jpeg";
        productList.add(product);


        recyclermarket.setAdapter(new macguffinco.hellrazorbarber.Services.Users.AdapterEmployees(productList, new macguffinco.hellrazorbarber.Services.Users.AdapterEmployees.OnItemClickListener() {
            @Override public void onItemClick(EmployeeDC barberDC) {

                new BarberListActivityTask(context,activity).execute(barberDC,null,null);
            }
        }));*/
    }
}
