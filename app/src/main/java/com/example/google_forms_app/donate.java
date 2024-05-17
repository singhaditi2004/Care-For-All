package com.example.google_forms_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.google_forms_app.databinding.ActivityDonateBinding;
import com.example.google_forms_app.databinding.ActivityVoluteerBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class donate extends AppCompatActivity {

    EditText name,emailid,phone,insta,dob;
    Button next;
    FrameLayout fragmentContainer;
    ActivityDonateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDonateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        name = findViewById(R.id.yourname);
        emailid = findViewById(R.id.emailid);
        phone = findViewById(R.id.phonenuber);
        dob = findViewById(R.id.dob);
        insta=findViewById(R.id.insta);
        fragmentContainer = findViewById(R.id.contain);

        Button next = findViewById(R.id.nextto);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yname = name.getText().toString();
                String email = emailid.getText().toString();
                String phon = phone.getText().toString();
                String date = dob.getText().toString();
                String instaid=insta.getText().toString();
                // Create a new instance of your fragment
                donorfragment fragment = new donorfragment();

                // Pass data to the fragment using Bundle
                Bundle bundle = new Bundle();
                bundle.putString("name", yname);
                bundle.putString("email", email);
                bundle.putString("phone", phon);
                bundle.putString("dob", date);
                bundle.putString("insta", instaid);
                fragment.setArguments(bundle);

                // Replace the entire activity's content with the fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.contain, fragment);
                transaction.addToBackStack(null);
                transaction.commit();


                // Hide the volunteer activity's content
                findViewById(R.id.cardViewdonor).setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        findViewById(R.id.cardViewdonor).setVisibility(View.VISIBLE);
    }

}