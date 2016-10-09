package ru.bus.raspisanie.shalk_off.raspisanie_bus.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters.ListAdapterRasp;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Caclulate;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Raspisanie;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

/**
 * Created by shalk_off on 06.10.2016.
 */
public class SgorodaFragment extends Fragment {
    ArrayList<Raspisanie> raspList = new ArrayList<>();
    ListAdapterRasp listAdapterRasp;
    ListView listView;
    String sGoroda;
    String[] sGorodaArray;
    Caclulate caclulate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_goroda, container, false);
        caclulate = new Caclulate();
        Intent intent = getActivity().getIntent();
        sGoroda = intent.getStringExtra("sGoroda");
        sGorodaArray = caclulate.parseAvto(sGoroda);
        // создаем адаптер
        getRaspisanie();
        listAdapterRasp = new ListAdapterRasp(getContext(), raspList);
        listView = (ListView) view.findViewById(R.id.list2);
        listView.setAdapter(listAdapterRasp);
        return view;
    }
    void getRaspisanie() {
        for (int i = 0; i < sGorodaArray.length; i++) {
            raspList.add(new Raspisanie(sGorodaArray[i], caclulate.status(sGorodaArray[i]), caclulate. image()));
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        listView.setAdapter(null);
    }
}
