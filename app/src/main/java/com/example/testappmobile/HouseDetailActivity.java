package com.example.testappmobile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class HouseDetailActivity extends Activity {

    private EditText etHouseId;
    private Button btnSearch;
    private Button btnBackToMenu; // ปุ่มสำหรับกลับไปยังหน้าเมนู
    private TextView tvAreaSize, tvNumBedrooms, tvNumBathrooms, tvPrice, tvHouseCondition, tvType, tvYearBuilt, tvParkingSpaces, tvAddress;
    private ImageView ivHouseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_detail);

        // เชื่อมโยง UI elements จาก XML
        etHouseId = findViewById(R.id.et_house_id);
        btnSearch = findViewById(R.id.btn_search);
        btnBackToMenu = findViewById(R.id.btn_back_to_menu); // เชื่อมโยงปุ่มย้อนกลับ
        tvAreaSize = findViewById(R.id.tv_area_size);
        tvNumBedrooms = findViewById(R.id.tv_num_bedrooms);
        tvNumBathrooms = findViewById(R.id.tv_num_bathrooms);
        tvPrice = findViewById(R.id.tv_price);
        tvHouseCondition = findViewById(R.id.tv_house_condition);
        tvType = findViewById(R.id.tv_type);
        tvYearBuilt = findViewById(R.id.tv_year_built);
        tvParkingSpaces = findViewById(R.id.tv_parking_spaces);
        tvAddress = findViewById(R.id.tv_address);
        ivHouseImage = findViewById(R.id.iv_house_image);

        // กดปุ่มค้นหาแล้วดึงข้อมูลจาก API
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ดึงหมายเลขบ้านที่ผู้ใช้กรอก
                String houseId = etHouseId.getText().toString().trim();

                // ตรวจสอบว่าใส่หมายเลขบ้านหรือยัง
                if (!houseId.isEmpty()) {
                    fetchHouseDetails(houseId);  // เรียกใช้ฟังก์ชันดึงข้อมูลบ้าน
                } else {
                    Toast.makeText(HouseDetailActivity.this, "กรุณาใส่หมายเลขบ้าน", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ตั้งค่าการทำงานให้ปุ่มย้อนกลับไปยัง MainActivity
        btnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // ปิดหน้าปัจจุบัน
            }
        });
    }

    // ฟังก์ชันสำหรับดึงข้อมูลบ้านจาก API
    private void fetchHouseDetails(String houseId) {
        String url = "http://10.0.2.2:3000/house/" + houseId;  // URL สำหรับดึงข้อมูลบ้านตาม houseId

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String areaSize = response.getString("area_size");
                            String numBedrooms = response.getString("num_bedrooms");
                            String numBathrooms = response.getString("num_bathrooms");
                            String price = response.getString("price");
                            String houseCondition = response.getString("house_condition");
                            String type = response.getString("type");
                            String yearBuilt = response.getString("year_built");
                            String parkingSpaces = response.getString("parking_spaces");
                            String address = response.getString("address");
                            String imageUri = response.getString("image_uri");
                            Log.d("ImageUri", imageUri);

                            // ตั้งค่าข้อมูลไปยัง TextView
                            tvAreaSize.setText(areaSize + " ตร.ม.");
                            tvNumBedrooms.setText(numBedrooms + " ห้องนอน");
                            tvNumBathrooms.setText(numBathrooms + " ห้องน้ำ");
                            tvPrice.setText(price + " บาท");
                            tvHouseCondition.setText(houseCondition);
                            tvType.setText(type);
                            tvYearBuilt.setText("สร้างเมื่อปี " + yearBuilt);
                            tvParkingSpaces.setText(parkingSpaces + " ที่จอดรถ");
                            tvAddress.setText(address);

                            // โหลดรูปภาพจาก URL โดยใช้ Glide
                            Glide.with(HouseDetailActivity.this)
                                    .load(imageUri)
                                    .placeholder(R.drawable.placeholder)  // รูปภาพขณะโหลด
                                    .error(R.drawable.error_image)        // รูปภาพเมื่อเกิดข้อผิดพลาด
                                    .into(ivHouseImage);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HouseDetailActivity.this, "เกิดข้อผิดพลาดในการแปลงข้อมูล", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error fetching house details: " + error.toString());
                        Toast.makeText(HouseDetailActivity.this, "ไม่พบข้อมูลบ้าน", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
