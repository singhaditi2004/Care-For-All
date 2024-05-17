package com.example.google_forms_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity2 extends AppCompatActivity {
    CardView cardView, cardView2, cardView3, cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Find card views by their IDs
        cardView = findViewById(R.id.cardView);
        cardView2 = findViewById(R.id.cardview2);
        cardView3 = findViewById(R.id.cardview3);
        cardView4 = findViewById(R.id.cardview4);

        // Set click listeners for card views
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentActivity("about");
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragmentActivity("gallery");
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, voluteer.class);
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, donate.class);
                startActivity(intent);
            }
        });
    }

    private void startFragmentActivity(String fragmentTag) {
        Intent intent = new Intent(MainActivity2.this, FragmentContainerActivity.class);
        intent.putExtra("FRAGMENT_TAG", fragmentTag);  // Use "FRAGMENT_TAG" as the key
        startActivity(intent);
    }
}
