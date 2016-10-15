package ru.bus.raspisanie.shalk_off.raspisanie_bus.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class SgorodaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    ArrayList<Raspisanie> raspList = new ArrayList<>();
    ListAdapterRasp listAdapterRasp;
    ListView listView;
    String sGoroda;
    String[] sGorodaArray;
    Caclulate caclulate;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_s_goroda, container, false);
        caclulate = new Caclulate();
        Intent intent = getActivity().getIntent();
        sGoroda = intent.getStringExtra("sGoroda");
        sGorodaArray = caclulate.parseAvto(sGoroda);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.sgoroda_swipe_refresh_layout);
       mSwipeRefreshLayout.setOnRefreshListener(this);
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
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                listView.setAdapter(null);
                raspList.clear();
                getRaspisanie();
                listAdapterRasp = new ListAdapterRasp(getContext(), raspList);
                listView.setAdapter(listAdapterRasp);
            }
        }, 400);
    }
}
