package ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters.FragmentsAdapter;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Fragments.SgorodaFragment;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Fragments.VgorodFragment;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

public class TimeListActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView textViewNomerAvto,textViewName1,textViewName2;
    private String color,nomerAvto,name1,name2, price, priceMoney;
    private int[] tabIcons = {
            R.drawable.ic_avtobus,
            R.drawable.ic_avtobus
    };
    private ImageView imageView,imagePrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_list);
        textViewNomerAvto = (TextView) findViewById(R.id.nomer_avtobusa);
        textViewName1 = (TextView) findViewById(R.id.textView_name1);
        textViewName2 = (TextView) findViewById(R.id.textView_name2);
        imageView = (ImageView) findViewById(R.id.imageBack);
        imagePrice = (ImageView) findViewById(R.id.imagePrice);

        //TODO Получить интентом id элементов из БД для заполнения: textViewNomerAvto,textViewName1,textViewName2
        Intent intent = getIntent();
        color = intent.getStringExtra("color");
        nomerAvto = intent.getStringExtra("nomerAvto");
        name1 = intent.getStringExtra("name1");
        name2 = intent.getStringExtra("name2");
        price = intent.getStringExtra("price");
        priceMoney = intent.getStringExtra("priceMoney");

        textViewNomerAvto.setText(nomerAvto);
        GradientDrawable gd = (GradientDrawable) textViewNomerAvto.getBackground().getCurrent();
        gd.setColor(Color.parseColor(color));
        textViewName1.setText(name1);
        textViewName2.setText(name2);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
       // tabLayout.setSelectedTabIndicatorColor(Color.parseColor(color));
      //  tabLayout.setTabTextColors(Color.parseColor("#ffffff"),Color.parseColor(color));
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0)
                {
                    textViewName1.setText(name1);
                    textViewName2.setText(name2);
                }
                else if (tab.getPosition()==1) {
                    textViewName1.setText(name2);
                    textViewName2.setText(name1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > 20){
                    finishAfterTransition();
                }
                else {
                    finish();
                }
            }
        });
        imagePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPrice = new Intent(TimeListActivity.this,PriceActivity.class);
                intentPrice.putExtra("price", price);
                intentPrice.putExtra("priceMoney",priceMoney);
                intentPrice.putExtra("nomer",nomerAvto);
                startActivity(intentPrice);
            }
        });

    }
    private void setupViewPager(ViewPager viewPager) {
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
        adapter.addFragment(new VgorodFragment(), "В город");
        adapter.addFragment(new SgorodaFragment(), "С города");
        //adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT > 20){
            finishAfterTransition();
        }
        else {
           finish();
        }

    }
    
}
