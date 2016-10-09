package ru.bus.raspisanie.shalk_off.raspisanie_bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class PriceActivity extends AppCompatActivity {
    String price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        Intent intent = getIntent();
        price = intent.getStringExtra("price");
        Toast.makeText(this, price, Toast.LENGTH_SHORT).show();
    }
}
