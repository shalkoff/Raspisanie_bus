package ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Price;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

/**
 * Created by shalk_off on 10.10.2016.
 */

public class ListAdapterPrice extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater1;
    ArrayList<Price> objects;

    public ListAdapterPrice(Context context, ArrayList<Price> products) {
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
            view = lInflater1.inflate(R.layout.items_list_price, parent, false);
        }
        Price p = getProduct(position);
        // заполняем View в пункте списка данными из товаров: время, время в пути
        // и картинка
        ((TextView) view.findViewById(R.id.textView)).setText(p.getName());
        ((TextView) view.findViewById(R.id.textView2)).setText(p.getPrice()+ "");
        return view;
    }

    // Время выезда по позиции
    Price getProduct(int position) {
        return ((Price) getItem(position));
    }
}
