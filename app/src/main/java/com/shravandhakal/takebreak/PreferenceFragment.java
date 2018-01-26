package com.shravandhakal.takebreak;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class PreferenceFragment extends Fragment {

    public static PreferenceFragment newInstance() {
        return new PreferenceFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preference_page, container, false);
        final EditText et = view.findViewById(R.id.editText4);
        Button np = view.findViewById(R.id.ConfirmBtn);
        np.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Display the newly selected number from picker
                Log.d("onclick", String.format("onClick: %s",et.getText().toString()));
                if(!(et.getText().toString().equals("")))
                {
                    int x= ReminderUtil.changeTime(Integer.parseInt(et.getText().toString()),getContext());
                    if(x==1)
                    {
                        Toast.makeText(getContext(), String.format("Update Sucecssful!\nNew Interval Time is now: %d",
                                Integer.parseInt(et.getText().toString())),
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
        return view;
    }
}
