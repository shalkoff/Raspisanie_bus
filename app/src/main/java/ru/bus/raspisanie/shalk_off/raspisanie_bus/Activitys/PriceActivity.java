package ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters.ListAdapterPrice;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Caclulate;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Price;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

public class PriceActivity extends AppCompatActivity {
    String price, priceMoney,nomer;
    ListView listPrice;
    String[] priceArray,priceMoneyArray;
    ArrayList<Price> listPriceItems = new ArrayList<>();
    ListAdapterPrice listAdapterPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        Intent intent = getIntent();
        price = intent.getStringExtra("price");
        priceMoney = intent.getStringExtra("priceMoney");
        nomer = intent.getStringExtra("nomer");
        setTitle("Цены "+nomer+" маршрута");
        Caclulate calc = new Caclulate();
        priceArray=calc.parseAvto(price);
        priceMoneyArray = calc.parseAvto(priceMoney);

        getRaspisanie();
        listAdapterPrice = new ListAdapterPrice(this, listPriceItems);
        listPrice = (ListView) findViewById(R.id.list_price);
        listPrice.setAdapter(listAdapterPrice);
        //Toast.makeText(this, price, Toast.LENGTH_SHORT).show();
    }
    void getRaspisanie() {
        for (int i = 0; i < priceArray.length; i++) {
            listPriceItems.add(new Price(priceArray[i],priceMoneyArray[i]));
        }

    }
}
