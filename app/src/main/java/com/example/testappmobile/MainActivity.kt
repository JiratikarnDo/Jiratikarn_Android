package com.example.testappmobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ปุ่มเพิ่มข้อมูล
        val btnAddData = findViewById<Button>(R.id.btn_add_data)
        btnAddData.setOnClickListener {
            val intent = Intent(this@MainActivity, InsertHouseActivity::class.java)
            startActivity(intent)
        }

        // ปุ่มเช็คข้อมูล
        val btnCheckData = findViewById<Button>(R.id.btn_check_data)
        btnCheckData.setOnClickListener {
            val intent = Intent(this@MainActivity, HouseDetailActivity::class.java)
            startActivity(intent)
        }
    }
}
