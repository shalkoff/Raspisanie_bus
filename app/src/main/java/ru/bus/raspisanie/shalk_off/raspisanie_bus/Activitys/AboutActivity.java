package ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

public class AboutActivity extends AppCompatActivity {
    TextView textViewData;
    SharedPreferences prefs;
    final String SAVED_TEXT = "data";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("О программе");
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        textViewData = (TextView) findViewById(R.id.data);
        textViewData.setText(prefs.getString(SAVED_TEXT, ""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private String getTextInPref() {
        prefs = getPreferences(MODE_PRIVATE);
        String savedText = prefs.getString(SAVED_TEXT, "");
        return savedText;
    }
}
