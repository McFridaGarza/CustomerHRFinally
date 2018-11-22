package macguffinco.hellrazorbarber.Activities.Dashboard;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Adapter extends FragmentPagerAdapter {

    Drawable myDrawable; //Drawable you want to display
    Context _context;
    public Adapter(FragmentManager fm,Context context) {
        super(fm);
        _context=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0: return new BranchDescriptionFragment();
            case 1: return new AppointmentsFragment();
           // case 2: return new GalleryFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position)  {
        String title="";
        switch (position) {

            case 0: {title=  "INICIO";break;}
            case 1: {title=  "MIS CITAS";break;}
            //case 2: {title=  "GALERIA";break;}
        }

        /*SpannableStringBuilder sb = new SpannableStringBuilder(); // space added before text for convenience

        // myDrawable = ContextCompat.getDrawable(, R.drawable.ic_baseline_event_24px);

       myDrawable=  ContextCompat.getDrawable(_context, R.drawable.ic_baseline_event_24px);

        myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(myDrawable, ImageSpan.ALIGN_BASELINE);
        sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
        return title;


    }





}
