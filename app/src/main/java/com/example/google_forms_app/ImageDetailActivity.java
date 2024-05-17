package com.example.google_forms_app;
// ImageDetailActivity.java
import android.os.Bundle;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class ImageDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        // Get the image URL from the intent
        String imageUrl = getIntent().getStringExtra("image_url");

        // Load the image into ImageView using Picasso library
        ImageView imageView = findViewById(R.id.image_view_detail);
        Picasso.get().load(imageUrl).into(imageView);
    }
}
