package com.example.paterson_kirsten_mdpcw;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//Kirsten Paterson S1828151
/**
 * A simple {@link Fragment} subclass.
 */
public class CDetailFrag extends Fragment {


    public CDetailFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cdetail, container, false);
        TextView tvDescription = (TextView) v.findViewById(R.id.tvDescription);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getString("selected_data") != null) {

                tvDescription.setText(Html.fromHtml(bundle.getString("selected_data")));

            }
            // Inflate the layout for this fragment


        }
        return v;
    }

}
