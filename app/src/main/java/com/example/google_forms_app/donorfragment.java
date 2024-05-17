package com.example.google_forms_app;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.getIntent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.google_forms_app.databinding.FragmentBottomFormBinding;
import com.example.google_forms_app.databinding.FragmentDonorfragmentBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class donorfragment extends Fragment {
    FragmentDonorfragmentBinding binding;
    private ImageView profilephoto;
    private ImageView camera;
    private String name, email, phone, dob,insta;
    private StorageReference storageReference;
    private Uri imageUri;
    private  ProgressDialog progressDialog;
      Button donor;
    public donorfragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDonorfragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        profilephoto =view.findViewById(R.id.donorphoto);
        camera = view.findViewById(R.id.camera1);
        // Inflate the layout for this fragment
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(donorfragment.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(180, 180)
                        .start();
            }
        });
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            email = bundle.getString("email");
            phone = bundle.getString("phone");
            dob = bundle.getString("dob");
            insta=bundle.getString("insta");
        }
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        binding.donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId= currentUser.getEmail();
                String[] parts = userId.split("@gmail.com");
                userId = parts[0];

                    // Now you have the user ID (userId) of the current logged-in user
                    Log.d("UserID", userId); // Print the user ID to Logcat or use it as needed
               // Bundle bundle = getArguments();

                  //  String userid = bundle.getString("yourKey");



                String upi = binding.upi.getText().toString();
                String amount = binding.amount.getText().toString();
                saveData(userId,name, phone, insta, dob, email,upi,amount);
                uploadImage();
            }
        });
        return view;
    }
    private void saveData(String userId,String name, String phonenumb, String insta, String dob, String emailid,String upi,String amount) {
        String url = "https://script.google.com/macros/s/AKfycbz7Jr7eFXjZhm2SzAuwhkjhYpcZ1BSqkMe5gMbReGSFIXAHYSb2qegDZNTCFzVHVultwA/exec?";
        url = url + "action=create&userId=" + userId + "&name=" + name + "&phonenumber=" + phonenumb + "&insta=" + insta + "&dob=" + dob + "&emailid=" + emailid + "&upi=" + upi + "&amount=" + amount;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                Log.d(response,"k");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(String.valueOf(error),"keyyyyy");
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profilephoto.setImageURI(imageUri);
        }
    }
    private void uploadImage() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);


        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        binding.donorphoto.setImageURI(null);
                        Toast.makeText(getContext(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(getContext(),"Failed to Upload",Toast.LENGTH_SHORT).show();


                    }
                });

    }
}