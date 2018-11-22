package macguffinco.hellrazorbarber.Activities.Dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import macguffinco.hellrazorbarber.R;

public class GridGalleryAdapter extends BaseAdapter {

    Context context;

    private final int[] images;
    View view;
    LayoutInflater layoutInflater;

    public GridGalleryAdapter(Context context,int[] images){
        super();
        this.context=context;
        this.images=images;


    }

    @Override
    public int getCount() {
       return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){

            view=new View(context);
            view= layoutInflater.inflate(R.layout.single_picture,null);
            ImageView image=(ImageView)view.findViewById(R.id.image_gallery);
            image.setImageResource(images[position]);

        }

        return view;
    }
}
