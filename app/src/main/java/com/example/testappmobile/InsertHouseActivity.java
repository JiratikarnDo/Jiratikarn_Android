package com.example.testappmobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

public class InsertHouseActivity extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText etAreaSize;
    private EditText etNumBedrooms;
    private EditText etNumBathrooms;
    private EditText etPrice;
    private EditText etHouseCondition;
    private EditText etType;
    private EditText etYearBuilt;
    private EditText etParkingSpaces;
    private EditText etAddress;
    private ImageView ivHouseImage;
    private Button btnSelectImage;
    private Button btnSave;
    private Button btnBackToHome;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_house);

        // Initialize the UI elements
        etAreaSize = findViewById(R.id.et_area_size);
        etNumBedrooms = findViewById(R.id.et_num_bedrooms);
        etNumBathrooms = findViewById(R.id.et_num_bathrooms);
        etPrice = findViewById(R.id.et_price);
        etHouseCondition = findViewById(R.id.et_house_condition);
        etType = findViewById(R.id.et_type);
        etYearBuilt = findViewById(R.id.et_year_built);
        etParkingSpaces = findViewById(R.id.et_parking_spaces);
        etAddress = findViewById(R.id.et_address);
        ivHouseImage = findViewById(R.id.iv_house_image);
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSave = findViewById(R.id.btn_save);
        btnBackToHome = findViewById(R.id.btn_back_to_home);

        // Button to select an image
        btnSelectImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Button to save house details
        btnSave.setOnClickListener(view -> {
            String areaSize = etAreaSize.getText().toString();
            String numBedrooms = etNumBedrooms.getText().toString();
            String numBathrooms = etNumBathrooms.getText().toString();
            String price = etPrice.getText().toString();
            String houseCondition = etHouseCondition.getText().toString();
            String type = etType.getText().toString();
            String yearBuilt = etYearBuilt.getText().toString();
            String parkingSpaces = etParkingSpaces.getText().toString();
            String address = etAddress.getText().toString();
            String imageUriString = imageUri != null ? imageUri.toString() : ""; // Handle if imageUri is null

            // Make a POST request to save the data
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "http://10.0.2.2:3000/add-house"; // Update this with your server URL

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(InsertHouseActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                            // Navigate back to MainActivity
                            Intent intent = new Intent(InsertHouseActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(InsertHouseActivity.this, "Error saving data", Toast.LENGTH_SHORT).show();
                            Log.e("VolleyError", error.toString());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("area_size", areaSize);
                    params.put("num_bedrooms", numBedrooms);
                    params.put("num_bathrooms", numBathrooms);
                    params.put("price", price);
                    params.put("house_condition", houseCondition);
                    params.put("type", type);
                    params.put("year_built", yearBuilt);
                    params.put("parking_spaces", parkingSpaces);
                    params.put("address", address);
                    params.put("image_uri", imageUriString); // Send image URI
                    return params;
                }
            };

            requestQueue.add(stringRequest);
        });

        btnBackToHome.setOnClickListener(view -> {
            Intent intent = new Intent(InsertHouseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Use Glide to display the selected image
            Glide.with(this)
                    .load(imageUri) // Load image from URI
                    .placeholder(R.drawable.placeholder) // Show placeholder while loading
                    .error(R.drawable.error_image) // Show error image if loading fails
                    .into(ivHouseImage); // Display image in ImageView
        }
    }
}
