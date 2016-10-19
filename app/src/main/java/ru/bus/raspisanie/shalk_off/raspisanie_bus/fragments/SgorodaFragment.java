package ru.bus.raspisanie.shalk_off.raspisanie_bus.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters.RecycleAdapterRasp;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Caclulate;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Raspisanie;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

/**
 * Created by shalk_off on 06.10.2016.
 */
public class SgorodaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    List<Raspisanie> raspList = new ArrayList<>();
    RecycleAdapterRasp recycleAdapterRasp;
    String sGoroda;
    String[] sGorodaArray;
    Caclulate caclulate;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View view;
    private RecyclerView recyclerView1;
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
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recycler2);
        recycleAdapterRasp = new RecycleAdapterRasp(raspList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView1.setAdapter(recycleAdapterRasp);
        recyclerView1.setHasFixedSize(true); // необязательно
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // необязательно
        recyclerView1.setLayoutManager(linearLayoutManager);
        recyclerView1.setItemAnimator(itemAnimator);
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
                raspList.clear();
                getRaspisanie();
                recycleAdapterRasp = new RecycleAdapterRasp(raspList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                recyclerView1.setAdapter(recycleAdapterRasp);
                recyclerView1.setHasFixedSize(true); // необязательно
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); // необязательно
                recyclerView1.setLayoutManager(linearLayoutManager);
                recyclerView1.setItemAnimator(itemAnimator);
            }
        }, 400);
    }
}
