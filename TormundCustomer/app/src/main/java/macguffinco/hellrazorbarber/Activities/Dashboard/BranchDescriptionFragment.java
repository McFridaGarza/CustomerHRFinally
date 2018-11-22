package macguffinco.hellrazorbarber.Activities.Dashboard;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Branches.HoraryDC;
import macguffinco.hellrazorbarber.Model.VaultFiles.VaultFileDC;
import macguffinco.hellrazorbarber.R;

public class BranchDescriptionFragment extends Fragment {
    public static final String TAG = BranchDescriptionFragment.class.getSimpleName();
Button agendar;
TextView txtCallPhoneNumber;
ImageView imgBranchDescription;
TextView mDescriptionBranch;
TextView mHoraries;
Button mButtonViewBranchMaps;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.fragment_branch_description,container,false);

        agendar=(Button)view.findViewById(R.id.agendarcita);
        agendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TormundManager.goDatesScreen(getContext());

            }
        });

        mButtonViewBranchMaps=(Button)view.findViewById(R.id.ButtonViewBranchMaps);
        mButtonViewBranchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getActivity(),BranchMapsActivity.class);
                startActivity(intent);
            }
        });


        mDescriptionBranch=view.findViewById(R.id.txtDescriptionBranch);

        mDescriptionBranch.setText(TormundManager.GlobalBranch.preference.description);

        SetBranchImageInDescriptionFragmentSection(view);
        SetCallPhoneNumberEvent(view);
        SetTextHoraries(view);


        return view;
    }


    public void SetBranchImageInDescriptionFragmentSection(View view){
        imgBranchDescription=view.findViewById(R.id.imgBranchDescription);
        if(imgBranchDescription!=null){

            String url="";

            Iterator<VaultFileDC> it = TormundManager.GlobalBranch.repo_files.iterator();
            while (it.hasNext()) {
                VaultFileDC current = it.next();
                if (current.type_vault_file==2) { //Medium Image
                    url=current.Url;
                    break;
                }
            }


            if(!url.equals("")){
                Picasso.with(getActivity())
                        .load(url)
                        .fit()
                        .into(imgBranchDescription);
            }


        }

    }
    public void SetCallPhoneNumberEvent(View view){

        txtCallPhoneNumber=view.findViewById(R.id.number_to_call);
        txtCallPhoneNumber.setText(TormundManager.GlobalBranch.Phone);
        txtCallPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v.findViewById(R.id.number_to_call);
                String phoneNumber = String.format("tel: %s",textView.getText().toString());
                // Create the intent.
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                // Set the data for the intent as the phone number.
                dialIntent.setData(Uri.parse(phoneNumber));
                // If package resolves to an app, send intent.
                if (dialIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    getActivity().startActivity(dialIntent);
                } else {
                    Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
                }

            }
        });

    }



    public void SetTextHoraries(View view){

        mHoraries=view.findViewById(R.id.mHoraries);
        if(TormundManager.GlobalBranch.preference==null) return;
        if(TormundManager.GlobalBranch.preference.horaries==null) return;

        SpannableStringBuilder builder=new SpannableStringBuilder();

        int count=35;

        ArrayList<HoraryDC> horaries=(ArrayList<HoraryDC>) TormundManager.GlobalBranch.preference.horaries.clone();

        Iterator<HoraryDC> it = horaries.iterator();
        while (it.hasNext()) {
            HoraryDC current = it.next();
            if (current.horary_type==1) {
                it.remove();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (HoraryDC horary:horaries ) {

            String dayname=TormundManager.DayOfWeekName(horary.day_of_week);

            if(sb.toString().contains(dayname)){
                sb.append("/"+horary.start+"-"+horary.finish);
                continue;
            }
            sb.append("\n");
           int r=0;
           sb.append(dayname+":");
            switch (dayname){
                case "Lunes": r=9; break;
                case "Martes": r=7; break;
                case "Miércoles": r=2; break;
                case "Jueves": r=7; break;
                case "Viernes": r=6; break;
                case "Sábado": r=6; break;
                case "Domingo": r=4; break;
            }

            for(int i=0;i<r;i++){
                sb.append(" ");

            }

            sb.append(horary.start+"-"+horary.finish);
           // builder.setSpan(new StyleSpan(Typeface.BOLD), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }


       // mHoraries.setText(Html.fromHtml(words,TO_HTML_PARAGRAPH_LINES_INDIVIDUAL));
        mHoraries.setText(sb.toString());


    }



    public void dialNumber(View view)
    {
        TextView textView = (TextView) view.findViewById(R.id.number_to_call);
        // Use format with "tel:" and phone number to create phoneNumber.
        String phoneNumber = String.format("tel: %s",
                textView.getText().toString());
        // Create the intent.
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        // Set the data for the intent as the phone number.
        dialIntent.setData(Uri.parse(phoneNumber));
        // If package resolves to an app, send intent.
        if (dialIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            getActivity().startActivity(dialIntent);
        } else {
            Log.e(TAG, "Can't resolve app for ACTION_DIAL Intent.");
        }

    }

}