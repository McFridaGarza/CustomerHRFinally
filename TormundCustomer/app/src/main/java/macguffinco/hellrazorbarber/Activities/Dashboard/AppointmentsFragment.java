package macguffinco.hellrazorbarber.Activities.Dashboard;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


import macguffinco.hellrazorbarber.Logic.TormundManager;
import macguffinco.hellrazorbarber.Model.Dates.DateDC;
import macguffinco.hellrazorbarber.Model.Employees.EmployeeDC;
import macguffinco.hellrazorbarber.R;

public class AppointmentsFragment extends Fragment {


    Button btnCreateAppointment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view= inflater.inflate(R.layout.activity_appointments_fragment,container,false);

        ArrayList<DateDC> appointmentsList=new ArrayList<DateDC>() ; Comparator<Timestamp> comparador= Collections.reverseOrder();

        btnCreateAppointment= view.findViewById(R.id.ButtonAppointment);
        btnCreateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TormundManager.goDatesScreen(getActivity());
            }
        });



        for(DateDC item :TormundManager.CustomerDates){

            appointmentsList.add(item);
        }

        RecyclerView recyclerAppoinment = (RecyclerView) view.findViewById(R.id.recycler_appointment);
        final FragmentActivity fa=getActivity();
        recyclerAppoinment.setLayoutManager(new LinearLayoutManager(fa));





        recyclerAppoinment.setAdapter(new macguffinco.hellrazorbarber.Services.Dates.AppointmentAdapter(appointmentsList));

      return view;
    }
}





