package com.example.google_forms_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container);

        Intent intent = getIntent();
        if (intent != null) {
            String fragmentToLoad = intent.getStringExtra("FRAGMENT_TAG");  // Use "FRAGMENT_TAG" to get the fragment name
            if (fragmentToLoad != null) {
                switch (fragmentToLoad) {
                    case "about":
                        replaceFragment(new About());
                        break;
                    case "gallery":
                        replaceFragment(new GalleryFragment());
                        break;
                    // Add more cases for other fragments if needed
                }
            } else {
                // Load a default fragment if no extra is provided
                replaceFragment(new About());
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
            finish();
        }
    }
}
