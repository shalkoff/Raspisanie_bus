package ru.bus.raspisanie.shalk_off.raspisanie_bus;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters.AdapterInfo;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DataBase.DBHelper;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Info;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest.JSONAvtobuses;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest.JSONVersion;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Services.APIService;

public class MainActivity extends AppCompatActivity {
    final static String LOG_TAG = "Log";
    final static String GET_ALL = "getAll";
    final static String GET_VERSION = "getVersion";
    private RecyclerView recyclerView1;
    private AdapterInfo adapter;
    private ProgressDialog pDialog;
    private List<Info> infoList = new ArrayList<>();
    private SharedPreferences prefs = null; // Чек первого запуска
    private SharedPreferences check = null; // Чек обновы
    final String SAVED_TEXT = "data";
    private APIService service;
    private TextView nomerAvto;
    private DBHelper dbHelper;
    private String dateObnoviServer;
    private String dateObnoviKlient;
    private AlertDialog.Builder ad;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://serverman4ik.myarena.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APIService.class);
        dbHelper = new DBHelper(this);
        nomerAvto = (TextView) findViewById(R.id.nomer_avtobusa);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView);
        //Чек обновления
        checkUpdate();
        zagruzikaIzSQLiteAll();
        recycleViewMhetod();





    }

    private void zagruzikaIzSQLiteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "ЖОПА");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        Cursor c = db.query(DBHelper.NAME_TABLE, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int idAvtoColIndex = c.getColumnIndex("idAvto");
            int nomer_avtoColIndex = c.getColumnIndex("nomerAvto");
            int name1ColIndex = c.getColumnIndex("name1");
            int name2ColIndex = c.getColumnIndex("name2");
            int v_gorodColIndex = c.getColumnIndex("vGorod");
            int s_gorodaColIndex = c.getColumnIndex("sGoroda");
            int colorColIndex = c.getColumnIndex("color");
            int priceColIndex = c.getColumnIndex("price");
            int priceMoneyColIndex = c.getColumnIndex("priceMoney");
            int detalsColIndex = c.getColumnIndex("detals");
            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", idAvto = " + c.getString(idAvtoColIndex) +
                                ", nomer_avto = " + c.getString(nomer_avtoColIndex) +
                                ", v_gorod = " + c.getString(v_gorodColIndex) +
                                ", s_goroda = " + c.getString(s_gorodaColIndex));
                infoList.add(new Info(c.getString(idAvtoColIndex),
                         c.getString(nomer_avtoColIndex),
                        c.getString(name1ColIndex),
                        c.getString(name2ColIndex),
                        c.getString(v_gorodColIndex),
                        c.getString(s_gorodaColIndex),
                        c.getString(colorColIndex),
                        c.getString(priceColIndex),
                        c.getString(priceMoneyColIndex),
                        c.getString(detalsColIndex)));
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
    }

    private void checkUpdate() {
        getVersionServer();
    }
    private void recycleViewMhetod()
    {
        adapter = new AdapterInfo(infoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView1.setAdapter(adapter);
        recyclerView1.setHasFixedSize(true); // необязательно
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // необязательно
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setItemAnimator(itemAnimator);
    }

    private String getTextInPref() {
        check = getPreferences(MODE_PRIVATE);
        String savedText = check.getString(SAVED_TEXT, "");
        return savedText;
    }
    private void saveTextInPref(String text) {
        check = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = check.edit();
        ed.putString(SAVED_TEXT, text);
        ed.commit();
    }
    private void getVersionServer()
    {
        Call<JSONVersion> call = service.getVers(GET_VERSION);
        call.enqueue(new Callback<JSONVersion>() {
            @Override
            public void onResponse(Call<JSONVersion> call, Response<JSONVersion> response) {
                if(response.isSuccessful()) {
                    JSONVersion test = new JSONVersion();
                    Gson gson = new Gson();
                    String json = gson.toJson(test);
                    Log.i(LOG_TAG, json);
                    dateObnoviKlient = getTextInPref();
                    dateObnoviServer = response.body().version;
                    if (!(dateObnoviServer.equals(dateObnoviKlient))) {
                            String title = "Обновление";
                            String message = "Расписание изменилось, обновить?";
                            String button1String = "Да";
                            String button2String = "Нет";

                            ad = new AlertDialog.Builder(MainActivity.this);
                            ad.setTitle(title);  // заголовок
                            ad.setMessage(message); // сообщение
                            ad.setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    saveTextInPref(dateObnoviServer);
                                    flag=true;
                                    infoList.clear();
                                    adapter.clearData();
                                    deleteSQL();
                                    getAllInfo();
                                    //Toast.makeText(MainActivity.this, "Вы сделали правильный выбор!",Toast.LENGTH_LONG).show();
                                }
                            });
                            ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    Toast.makeText(MainActivity.this, "Ну лан :(", Toast.LENGTH_LONG)
                                            .show();


                                }
                            });
                            ad.setCancelable(true);
                            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) {
                                    Toast.makeText(MainActivity.this, "Вы ничего не выбрали",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                            try {
                                ad.show();
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Ошибка проверки обновления", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            // Toast.makeText(MainActivity.this, "Cоединения с сетью есть, но менять ничто не нужно!", Toast.LENGTH_SHORT).show();
                        }
                }
            }
            @Override
            public void onFailure(Call<JSONVersion> call, Throwable t) {
                //Toast.makeText(MainActivity.this, "Ошибка проверки обновления!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAllInfo()
    {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Загрузка расписания...");
        pDialog.setCancelable(false);
        pDialog.show();
        Call<JSONAvtobuses> call = service.getAllInfo(GET_ALL);
        call.enqueue(new Callback<JSONAvtobuses>() {
            @Override
            public void onResponse(Call<JSONAvtobuses> call, Response<JSONAvtobuses> response) {
                JSONAvtobuses test = new JSONAvtobuses();
                if (response.isSuccessful())
                {
                    Gson gson = new Gson();
                    String json = gson.toJson(test);
                    Log.i(LOG_TAG,json);
                    List<JSONAvtobuses.Bu> listInfo1 = new ArrayList<>();
                    listInfo1 = response.body().bus;
                    insertData(listInfo1);
                    if(flag) {
                        zagruzikaIzSQLiteAll();
                        recycleViewMhetod();
                    }
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                } else {
                    // Ошибка
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<JSONAvtobuses> call, Throwable t) {
                if (pDialog.isShowing())
                    pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ошибка загрузки расписания!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertData(List<JSONAvtobuses.Bu> list) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            contentValues.put("idAvto", ""+list.get(i).idAvto);
            contentValues.put("nomerAvto", ""+list.get(i).nomerAvto);
            contentValues.put("name1", ""+list.get(i).name1);
            contentValues.put("name2", ""+list.get(i).name2);
            contentValues.put("vGorod", ""+list.get(i).vGorod);
            contentValues.put("sGoroda", ""+list.get(i).sGoroda);
            contentValues.put("color", ""+list.get(i).color);
            contentValues.put("price", ""+list.get(i).price);
            contentValues.put("priceMoney", ""+list.get(i).priceMoney);
            contentValues.put("detals", ""+list.get(i).detals);
            long rowID = db.insert(DBHelper.NAME_TABLE, null, contentValues);
            Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        }
    }
    private void deleteSQL() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Удалить нахуй");
        // удаляем все записи
        int clearCount = db.delete(DBHelper.NAME_TABLE, null, null);
        Log.d(LOG_TAG, "deleted rows count = " + clearCount);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settingsMenu:
                //intent = new Intent(MainActivity.this, spravka.class);
              //  startActivity(intent);
                return true;
            case R.id.oProg:
               // intent = new Intent(this, oProg.class);
             //   startActivity(intent);
                return true;
           /* case R.id.error:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=ru.bus.raspisanie.shalk_off.raspisanie_bus"));
                startActivity(intent);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

