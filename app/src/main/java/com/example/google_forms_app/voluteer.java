package com.example.google_forms_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class voluteer extends AppCompatActivity {

    EditText name, emailid, phone, dob;
    FrameLayout fragmentContainer;
    View volunteerContent;
    private Fragment switchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voluteer);

        name = findViewById(R.id.yourname);
        emailid = findViewById(R.id.emailid);
        phone = findViewById(R.id.phonenuber);
        dob = findViewById(R.id.dob);
        fragmentContainer = findViewById(R.id.fragment_container);


        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yname = name.getText().toString();
                String email = emailid.getText().toString();
                String phon = phone.getText().toString();
                String date = dob.getText().toString();

                // Create a new instance of your fragment
                BottomFormFragment fragment = new BottomFormFragment();

                // Pass data to the fragment using Bundle
                Bundle bundle = new Bundle();
                bundle.putString("name", yname);
                bundle.putString("email", email);
                bundle.putString("phone", phon);
                bundle.putString("dob", date);
                fragment.setArguments(bundle);

                // Replace the entire activity's content with the fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.addToBackStack(null);
                transaction.commit();


                // Hide the volunteer activity's content
                findViewById(R.id.cardViewa).setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        findViewById(R.id.cardViewa).setVisibility(View.VISIBLE);
    }
}
