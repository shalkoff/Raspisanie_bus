package ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters.AdapterInfo;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.AppRater;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DataBase.DBHelper;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Info;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.GetDiveceName;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest.JSONAvtobuses;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.JSONRequest.JSONVersion;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Services.APIService;

public class MainActivity extends AppCompatActivity {
    final static String LOG_TAG = "Log";
    final static String GET_ALL = "getAll";
    final static String GET_VERSION = "getVersion";
    private RecyclerView recyclerView1;
    private AdapterInfo adapter;
    private ProgressDialog pDialog;
    private List<Info> infoList = new ArrayList<>();
    private SharedPreferences prefs = null; // Чек уведомления об обновлении
    private SharedPreferences check = null; // Чек обновы
    final String SAVED_TEXT = "data";
    private APIService service;
    private TextView nomerAvto;
    private DBHelper dbHelper;
    private String dateObnoviServer;
    private String dateObnoviKlient;
    private AlertDialog.Builder ad;
    private boolean flag = false;
    private String nameDivece;
    private String token;
    private Button buttonZagrRasp;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppRater.app_launched(this);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://serverman4ik.myarena.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        textView = (TextView) findViewById(R.id.textView9);
        textView.setVisibility(View.GONE);
        buttonZagrRasp = (Button) findViewById(R.id.downloaded_rasp);
        buttonZagrRasp.setVisibility(View.GONE);
        buttonZagrRasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zagrRasp();
            }
        });

        service = retrofit.create(APIService.class);
        dbHelper = new DBHelper(this);
        nomerAvto = (TextView) findViewById(R.id.nomer_avtobusa);
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView);

        //Получаем токен имодель телефона
        token = FirebaseInstanceId.getInstance().getToken();
        GetDiveceName getDiveceName = new GetDiveceName();
        nameDivece = getDiveceName.getDeviceName();
        setTokenToMyServer(nameDivece,token);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Чек обновления
        if (prefs.getBoolean("checkupdate", true)) {
            checkUpdate();
        }
        zagruzikaIzSQLiteAll();
        recycleViewMhetod();


    }

    private void zagrRasp() {
        getVersionServerWhenNotInternet();
    }

    private void setTokenToMyServer(String nameDivece, String token) {
        //отправляем запрос на мой сервак, в php надо сделать проверку на валидность токена.
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
        if(infoList.isEmpty())
        {
            textView.setVisibility(View.VISIBLE);
            buttonZagrRasp.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            buttonZagrRasp.setVisibility(View.GONE);

            adapter = new AdapterInfo(infoList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            recyclerView1.setAdapter(adapter);
            recyclerView1.setHasFixedSize(true); // необязательно
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // необязательно
            recyclerView1.setLayoutManager(linearLayoutManager);
            recyclerView1.setItemAnimator(itemAnimator);
        }
    }

    private String getTextInPref() {
        //check = getPreferences(MODE_PRIVATE);
        check = PreferenceManager.getDefaultSharedPreferences(this);
        String savedText = check.getString(SAVED_TEXT, "");
        return savedText;
    }
    private void saveTextInPref(String text) {
       // check = getPreferences(MODE_PRIVATE);
        check = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor ed = check.edit();
        ed.putString(SAVED_TEXT, text);
        ed.commit();
    }
    private void getVersionServerWhenNotInternet(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Response<JSONVersion> response;
                final Call<JSONVersion> call = service.getVers(GET_VERSION);
                try {
                    response = call.execute();
                    if(response.isSuccessful()) {
                        handlerPdialogShowVersion.sendEmptyMessage(0);
                        JSONVersion test = new JSONVersion();
                        Gson gson = new Gson();
                        String json = gson.toJson(test);
                        Log.i(LOG_TAG, json);
                        dateObnoviKlient = getTextInPref();
                        dateObnoviServer = response.body().version;
                        if (!(dateObnoviServer.equals(dateObnoviKlient))) {
                            //Когда строка локальная не совпадает с серверной, то выполняется тут
                            saveTextInPref(dateObnoviServer);
                            flag=true;
                            getAllInfo();
                        } else {
                            // Toast.makeText(MainActivity.this, "Cоединения с сетью есть, но менять ничто не нужно!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    handlerPdialogDiss.sendEmptyMessage(0);

                } catch (IOException e) {
                    handlerToast.sendEmptyMessage(0);
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void getVersionServer()
    {
        textView.setVisibility(View.GONE);
        buttonZagrRasp.setVisibility(View.GONE);
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
                                    //adapter.clearData();
                                    deleteSQL();
                                    getAllInfo();
                                    //Toast.makeText(MainActivity.this, "Вы сделали правильный выбор!",Toast.LENGTH_LONG).show();
                                }
                            });
                            ad.setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int arg1) {
                                    //Toast.makeText(MainActivity.this, "Ну лан :(", Toast.LENGTH_LONG).show();
                                }
                            });
                            ad.setCancelable(true);
                            ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                public void onCancel(DialogInterface dialog) {
                                   // Toast.makeText(MainActivity.this, "Вы ничего не выбрали", Toast.LENGTH_LONG).show();
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
    Handler handlerPdialogDiss = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pDialog.dismiss();
        }
    };
    Handler handlerPdialogShow = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Загрузка расписания...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
    };
    Handler handlerPdialogShowVersion = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Проверка версии...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
    };
    Handler handlerToast = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(MainActivity.this, "Проверьте подключение к интернету!", Toast.LENGTH_SHORT).show();
        }
    };
    Handler handlerRecyclerView = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            recycleViewMhetod();
        }
    };
    private void getAllInfo()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Call<JSONAvtobuses> call = service.getAllInfo(GET_ALL);
                Response<JSONAvtobuses> response;
                JSONAvtobuses test = new JSONAvtobuses();
                try {
                    handlerPdialogShow.sendEmptyMessage(0);
                    response = call.execute();
                    if (response.isSuccessful())
                    {
                        Gson gson = new Gson();
                        String json = gson.toJson(test);
                        Log.i(LOG_TAG,json);
                        List<JSONAvtobuses.Bu> listInfo1;
                        listInfo1 = response.body().bus;
                        insertData(listInfo1);
                        if(flag) {
                            zagruzikaIzSQLiteAll();
                            handlerRecyclerView.sendEmptyMessage(0); // заполняем handlerRecyclerView
                        }
                    } else {
                        // Ошибка
                    }
                    handlerPdialogDiss.sendEmptyMessage(0); // закрываем pDialog

                } catch (IOException e) {
                    e.printStackTrace();
                    handlerToast.sendEmptyMessage(0);
                    handlerPdialogDiss.sendEmptyMessage(0); // закрываем pDialog
                }
            }
        }).start();
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
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.oProg:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

