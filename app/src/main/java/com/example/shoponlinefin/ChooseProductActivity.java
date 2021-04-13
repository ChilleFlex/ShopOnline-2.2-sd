package com.example.shoponlinefin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);
        this.setTitle("Category");
    }
}
