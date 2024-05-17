package com.example.google_forms_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public FirebaseAuth mAuth;
    EditText emailTextInput;
    EditText passwordTextInput;
    Button signInButton;
    Button forgotPasswordButton;
    Button sendVerifyMailAgainButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailTextInput = findViewById(R.id.id);
        passwordTextInput = findViewById(R.id.password);
        signInButton = findViewById(R.id.login_button);
       // String email=emailTextInput.getText().toString()+"@gmail.com";

        mAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = emailTextInput.getText().toString();
                String email;


                // Check if the entered email already contains "@gmail.com"
                if (!enteredEmail.endsWith("@gmail.com")) {
                    // If not, concatenate "@gmail.com"

                    email = enteredEmail + "@gmail.com";

                } else {
                    email = enteredEmail;
                }
                if (email.contentEquals("")) {


                    Toast.makeText(MainActivity.this, "Email cant be empty", Toast.LENGTH_SHORT).show();


                } else if (passwordTextInput.getText().toString().contentEquals("")) {

                    Toast.makeText(MainActivity.this, "Password cant be empty", Toast.LENGTH_SHORT).show();

                } else {

                    mAuth.signInWithEmailAndPassword(email, passwordTextInput.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");

                                        FirebaseUser user = mAuth.getCurrentUser();


                                        goToSecondActivity();


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                        Log.e(email,"k");
                                    }

                                }
                            });


                }


            }
        });


        EditText editText = findViewById(R.id.id);
        EditText editText2 = findViewById(R.id.password);
        Button button=findViewById(R.id.login_button);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // EditText पर क्लिक करने पर बैकग्राउंड बदलें
                    editText.setBackgroundResource(R.drawable.edittext_rounded_corners);
                } else {
                    // EditText पर क्लिक करने के बाद बैकग्राउंड बदलें
                    editText.setBackgroundResource(R.drawable.loginborder);
                }
            }
        });
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // EditText पर क्लिक करने पर बैकग्राउंड बदलें
                    editText2.setBackgroundResource(R.drawable.edittext_rounded_corners);
                } else {
                    // EditText पर क्लिक करने के बाद बैकग्राउंड बदलें
                    editText2.setBackgroundResource(R.drawable.loginborder);
                }
            }
        });
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // EditText पर क्लिक करने पर बैकग्राउंड बदलें
                    button.setBackgroundResource(R.color.login_red);
                } else {
                    // EditText पर क्लिक करने के बाद बैकग्राउंड बदलें
                    button.setBackgroundResource(R.drawable.loginborder);
                }
            }
        });
        // Firebase Realtime Database ka reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference counterRef = database.getReference("counter");

// TimerTask ka schedule
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Counter ki value ko retrieve kare
                counterRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // Counter ki current value ko retrieve kare
                        Integer counterValue = dataSnapshot.getValue(Integer.class);
                        // Counter ki value ko increment kare aur update kare
                        if (counterValue != null) {
                            counterRef.setValue(counterValue + 1);
                        } else {
                            // Agar counter ki value null hai, toh use 0 se start kare
                            counterRef.setValue(0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Error handling
                        Log.e(TAG, "Counter value retrieval failed.", databaseError.toException());
                    }
                });
            }
        },  0, 6 * 60 * 60 * 1000); // 5 seconds ke intervals mein


        TextView counterTextView = findViewById(R.id.counterTextView);

        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer counterValue = dataSnapshot.getValue(Integer.class);
                if (counterValue != null) {
                    counterTextView.setText(String.valueOf(counterValue));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error handling
                Log.e(TAG, "Counter value retrieval failed.", databaseError.toException());
            }
        });// 6 ghante ke intervals mein

    }
        // Method to navigate to the second activity
        private void goToSecondActivity () {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();
        }


}
