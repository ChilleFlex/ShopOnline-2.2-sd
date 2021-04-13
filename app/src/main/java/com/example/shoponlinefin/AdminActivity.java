package com.example.shoponlinefin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private ImageView tshirts,watches,shoes,female_dresses,glasses,laptops;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        this.setTitle("Admin panel");

        tshirts = (ImageView)findViewById(R.id.tshirts);
        watches = (ImageView)findViewById(R.id.watches);
        shoes = (ImageView)findViewById(R.id.shoes);
        female_dresses = (ImageView)findViewById(R.id.female_dresses);
        laptops = (ImageView)findViewById(R.id.laptops);
        glasses = (ImageView)findViewById(R.id.glasses);

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,AdminAddNewProductActivity.class);
                i.putExtra("category","tshirts");
                startActivity(i);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,AdminAddNewProductActivity.class);
                i.putExtra("category","watches");
                startActivity(i);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,AdminAddNewProductActivity.class);
                i.putExtra("category","shoes");
                startActivity(i);
            }
        });

        female_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,AdminAddNewProductActivity.class);
                i.putExtra("category","female_dresses");
                startActivity(i);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,AdminAddNewProductActivity.class);
                i.putExtra("category","laptops");
                startActivity(i);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,AdminAddNewProductActivity.class);
                i.putExtra("category","glasses");
                startActivity(i);
            }
        });
    }
}
