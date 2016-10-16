package ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.Caclulate;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

public class DetalsMarshActivity extends AppCompatActivity {
    String nomer,detals;
    TextView textViewMarshSled, textViewTehInfo,textViewOstanovki,textViewKompaniya,textViewAddress,textViewPhone;
    String[] itemsArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detals_marsh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textViewMarshSled = (TextView) findViewById(R.id.marsh_sled);
        textViewTehInfo = (TextView) findViewById(R.id.teh_info);
        textViewOstanovki = (TextView) findViewById(R.id.ostanovok);
        textViewKompaniya = (TextView) findViewById(R.id.perevoschik);
        textViewAddress = (TextView) findViewById(R.id.address);
        textViewPhone = (TextView) findViewById(R.id.telephone);
        Intent intent = getIntent();
        nomer = intent.getStringExtra("nomer");
        detals = intent.getStringExtra("detals");
        setTitle("Детали маршрута: "+nomer);
        Caclulate caclulate = new Caclulate();
        itemsArray = caclulate.parseAvto(detals);
        textViewMarshSled.setText(itemsArray[0]);
        textViewTehInfo.setText(itemsArray[1]);
        textViewOstanovki.setText(itemsArray[2]);
        textViewKompaniya.setText(itemsArray[3]);
        textViewAddress.setText(itemsArray[4]);
        textViewPhone.setText(itemsArray[5]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
