package ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Info;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Raspisanie;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;

/**
 * Created by shalk_off on 19.10.2016.
 */

public class RecycleAdapterRasp extends RecyclerView.Adapter<RecycleAdapterRasp.ViewHolder> {
    List<Raspisanie> objects;

    public RecycleAdapterRasp(List<Raspisanie> objects) {
        this.objects = objects;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_list_raspisanie, parent, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Raspisanie raspisanie = objects.get(position);
        holder.mTextViewtvDescr.setText(raspisanie.getTime_otp());
        holder.mTextViewtvPrice.setText(raspisanie.getTime());
        holder.mImageViewivImage.setImageResource(raspisanie.getImage());
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        public final TextView mTextViewtvDescr,mTextViewtvPrice;
        public final ImageView mImageViewivImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTextViewtvDescr = (TextView) view.findViewById(R.id.tvDescr);
            mTextViewtvPrice = (TextView) view.findViewById(R.id.tvPrice);
            mImageViewivImage = (ImageView)  view.findViewById(R.id.ivImage1);
        }
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }
}
