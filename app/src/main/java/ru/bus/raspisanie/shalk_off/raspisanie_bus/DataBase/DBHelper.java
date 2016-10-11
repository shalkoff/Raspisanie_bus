package ru.bus.raspisanie.shalk_off.raspisanie_bus.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shalk_off on 08.10.2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    final static String LOG_TAG = "Log";
    final static int VERSION_DB = 1;
    public static String NAME_TABLE = "raspisanieBusV3";
    final static String ROW_IDAVTO = "idAvto";
    final static String ROW_NOMERAVTO = "nomerAvto";
    final static String ROW_NAME1 = "name1";
    final static String ROW_NAME2 = "name2";
    final static String ROW_VGOROD = "vGorod";
    final static String ROW_SGORODA = "sGoroda";
    final static String ROW_COLOR = "color";
    final static String ROW_PRICE = "price";
    final static String ROW_PRICE_MONEY = "priceMoney";
    final static String ROW_DETALS = "detals";

    public DBHelper(Context context) {
        super(context, "DataBese", null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "Создание базы данных");
        // создаем таблицу с полями
        db.execSQL("create table "+NAME_TABLE+" ("
                + "id integer primary key autoincrement,"
                + ROW_IDAVTO+" text,"
                + ROW_NOMERAVTO+" text,"
                + ROW_NAME1+" text,"
                + ROW_NAME2+" text,"
                + ROW_VGOROD+" text,"
                + ROW_SGORODA+" text,"
                + ROW_COLOR+" text,"
                + ROW_PRICE+" text,"
                + ROW_PRICE_MONEY+" text,"
                + ROW_DETALS+" text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
