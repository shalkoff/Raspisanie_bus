package ru.bus.raspisanie.shalk_off.raspisanie_bus.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters.ListAdapterRasp;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Caclulate;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Raspisanie;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

/**
 * Created by shalk_off on 06.10.2016.
 */
public class VgorodFragment extends Fragment {
    ArrayList<Raspisanie> raspList = new ArrayList<>();
    ListAdapterRasp listAdapterRasp;
    String vGorod;
    String[] vGorodArray;
    Caclulate caclulate;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_v_gorod, container, false);
        caclulate = new Caclulate();
        Intent intent = getActivity().getIntent();
        vGorod = intent.getStringExtra("vGorod");
        vGorodArray = caclulate.parseAvto(vGorod);

        // создаем адаптер
        getRaspisanie();
        listAdapterRasp = new ListAdapterRasp(getContext(), raspList);
        ListView listView = (ListView) view.findViewById(R.id.list1);
        listView.setAdapter(listAdapterRasp);
        return view;
    }
    void getRaspisanie() {
        for (int i = 0; i < vGorodArray.length; i++) {
            raspList.add(new Raspisanie(vGorodArray[i], caclulate.status(vGorodArray[i]), caclulate. image()));
        }

    }
}
