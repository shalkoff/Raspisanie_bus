package ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Raspisanie;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

/**
 * Created by shalk_off on 09.10.2016.
 */

public class ListAdapterRasp extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater1;
    ArrayList<Raspisanie> objects;

    public ListAdapterRasp(Context context, ArrayList<Raspisanie> products) {
        ctx = context;
        objects = products;
        lInflater1 = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater1.inflate(R.layout.items_list_raspisanie, parent, false);
        }
        Raspisanie p = getProduct(position);
        // заполняем View в пункте списка данными из товаров: время, время в пути
        // и картинка
        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.getTime_otp());
        ((TextView) view.findViewById(R.id.tvPrice)).setText(p.getTime()+ "");
        ((ImageView) view.findViewById(R.id.ivImage1)).setImageResource(p.getImage());
        return view;
    }

    // Время выезда по позиции
    Raspisanie getProduct(int position) {
        return ((Raspisanie) getItem(position));
    }
}
