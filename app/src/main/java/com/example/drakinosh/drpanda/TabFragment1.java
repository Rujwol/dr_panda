package com.example.drakinosh.drpanda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TabFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment1, container, false);


        Button create_but = view.findViewById(R.id.create_p_but);
        create_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateFormActivity.class);
                startActivity(intent);
            }
        });


        Button view_but = view.findViewById(R.id.view_p_but);
         view_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), ViewFormActivity.class);
                startActivity(myIntent);
            }
        });

        return view;
    }
}