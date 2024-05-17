package com.example.google_forms_app;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.google_forms_app.databinding.FragmentBottomFormBinding;
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

public class BottomFormFragment extends Fragment {

    private FragmentBottomFormBinding binding; // Use ViewBinding
    private ImageView profilephoto;
    private ImageView camera;
    private Uri imageUri;
    private TextView statusTV;
    private String name, email, phone, dob;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private RadioGroup radioGroup;
    private EditText insta;

    public BottomFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBottomFormBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        insta = view.findViewById(R.id.insta);
        profilephoto = view.findViewById(R.id.profilephoto);
        camera = view.findViewById(R.id.camera);
        radioGroup = view.findViewById(R.id.groupradio);
        radioGroup.clearCheck();

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(BottomFormFragment.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(180, 180)
                        .start();
            }
        });

        statusTV = view.findViewById(R.id.idTVStatus);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                statusTV.setText(radioButton.getText());
            }
        });

        // Initialize other views and set listeners

        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            email = bundle.getString("email");
            phone = bundle.getString("phone");
            dob = bundle.getString("dob");


        }

            binding.submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    String userId= currentUser.getEmail();
                    String[] parts = userId.split("@gmail.com");
                    userId = parts[0];                    // You may need to initialize other variables here as well
                    insta = view.findViewById(R.id.insta);
                    statusTV = view.findViewById(R.id.idTVStatus);
                    // Call methods to save data and upload image
                    String instat=insta.getText().toString();
                    String status=statusTV.getText().toString();
                    saveData(userId,name, phone, instat, dob, status, email);
                    uploadImage();
                }

            });

        return view;
    }

    private void saveData(String userId,String name, String phonenumb, String insta, String dob, String stauts, String emailid) {
        String url = "https://script.google.com/macros/s/AKfycbxGximIdT6LhzbJrLBxLEFsKqB91CLfUjAGcj8YbIiNkNaEAuqz34E4Kj59s7F37uyviA/exec?";
        url = url + "action=create&userId=" + userId + "&name=" + name + "&phonenumb=" + phonenumb + "&insta=" + insta + "&dob=" + dob + "&idTVStatus=" + stauts + "&emailid=" + emailid;

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
        if (requestCode == ImagePicker.REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
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
        storageReference = FirebaseStorage.getInstance().getReference("volunteer/"+fileName);


        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        binding.profilephoto.setImageURI(null);
                        Toast.makeText(getContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
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
    // Method to send data to the spreadsheet

}
