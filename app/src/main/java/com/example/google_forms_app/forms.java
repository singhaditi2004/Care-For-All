package com.example.google_forms_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class forms extends Fragment {

    ImageView donor;
    ImageView volunt;

    public forms() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_forms, container, false);
        donor = rootView.findViewById(R.id.donor);
        volunt = rootView.findViewById(R.id.volunt);

        // Set click listener for donor image
        setClickListenerForActivityLaunch(donor, donate.class);

        // Set click listener for volunteer image
        setClickListenerForActivityLaunch(volunt, voluteer.class);

        return rootView;
    }


    private void setClickListenerForActivityLaunch(ImageView donor, final Class<?> activityClass) {
        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), activityClass);
                startActivity(intent);
            }
        });
    }



}
