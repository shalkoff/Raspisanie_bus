package ru.bus.raspisanie.shalk_off.raspisanie_bus.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Info;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.DescribingClasses.Raspisanie;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys.DetalsMarshActivity;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys.PriceActivity;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.R;
import ru.bus.raspisanie.shalk_off.raspisanie_bus.Activitys.TimeListActivity;

public class AdapterInfo extends RecyclerView.Adapter<AdapterInfo.MyViewHolder> {


    private Context context;
    private List<Info> infoList;
    private List<Raspisanie> raspisanieList;
    private int posGlobal;
    public AdapterInfo(List<Info> infoList) {
        this.infoList = infoList;
    }

    @Override
    public AdapterInfo.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.items_list_avto, viewGroup, false);
        context = view.getContext();
        return new MyViewHolder(view);
    }
    public void clearData() {
        int size = this.infoList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.infoList.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }
    @Override
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onBindViewHolder(final AdapterInfo.MyViewHolder holder, final int position) {
        posGlobal=position;
        Info info = infoList.get(position);
        holder.nomerAvto.setText(info.getNomerAvto());
        GradientDrawable gd = (GradientDrawable) holder.nomerAvto.getBackground().getCurrent();
        gd.setColor(Color.parseColor(info.getColor()));
      //  gd.setCornerRadii(new float[]{30, 30, 30, 30, 0, 0, 30, 30});
       // gd.setStroke(2, Color.parseColor("#00FFFF"), 5, 6);





        holder.textView_name1.setText(info.getName1());
        holder.textView_name2.setText(info.getName2());
        holder.imageButton_settings.setImageResource(R.drawable.ic_more_vert_black_24dp);
        Log.i("RecyclerView", "Позиция: " + position);

        final int clickPos = position;
        holder.imageButton_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("RecyclerView", "Вы щёлкнули на позиции " + clickPos);
                //Toast.makeText(context, "щёлкнули", Toast.LENGTH_SHORT).show();
                //Открывать меню с настройками
                showPopupMenu(v,clickPos);

            }
        });

        holder.linerBlock.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Log.i("RecyclerView", "Вы щёлкнули на позиции " + clickPos);
                Intent intent = new Intent(context, TimeListActivity.class);
                ActivityOptions options;
                //
                intent.putExtra("color", infoList.get(position).getColor());
                intent.putExtra("nomerAvto", infoList.get(position).getNomerAvto());
                intent.putExtra("name1", infoList.get(position).getName1());
                intent.putExtra("name2", infoList.get(position).getName2());
                //
                intent.putExtra("vGorod", infoList.get(position).getvGorod());
                intent.putExtra("sGoroda", infoList.get(position).getsGoroda());
                intent.putExtra("price", infoList.get(position).getPrice());
                intent.putExtra("priceMoney", infoList.get(position).getPriceMoney());
               // context.startActivity(intent, options.toBundle());
                if (Build.VERSION.SDK_INT > 20){
                    Pair pair1 = Pair.create(holder.nomerAvto, "nomerAvto");
                    Pair pair2 = Pair.create(holder.textView_name1, "name1");
                    Pair pair3 = Pair.create(holder.textView_name2, "name2");
                    options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair1, pair2, pair3);
                    context.startActivity(intent, options.toBundle());
                }
                else {
                    context.startActivity(intent);
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nomerAvto;
        private TextView textView_name1;
        private TextView textView_name2;
        private ImageButton imageButton_settings;
        private LinearLayout linerBlock;
        private Menu linerBlock1;

        public MyViewHolder(View itemView) {
            super(itemView);
            nomerAvto = (TextView) itemView.findViewById(R.id.nomer_avtobusa);
            textView_name1 = (TextView) itemView.findViewById(R.id.textView_name1);
            textView_name2 = (TextView) itemView.findViewById(R.id.textView_name2);
            imageButton_settings= (ImageButton) itemView.findViewById(R.id.imageView);
            linerBlock = (LinearLayout) itemView.findViewById(R.id.liner_block);
        }
    }

    private void showPopupMenu(View v, final int position) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        popupMenu.inflate(R.menu.avto_menu); // Для Android 4.0
        // для версии Android 3.0 нужно использовать длинный вариант
        // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
        // popupMenu.getMenu());

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // Toast.makeText(PopupMenuDemoActivity.this,
                        // item.toString(), Toast.LENGTH_LONG).show();
                        // return true;
                        Intent intent;
                        switch (item.getItemId()) {

                            case R.id.price_menu:
                                intent = new Intent((Activity) context,PriceActivity.class);
                                intent.putExtra("price", infoList.get(position).getPrice());
                                intent.putExtra("priceMoney", infoList.get(position).getPriceMoney());
                                intent.putExtra("nomer", infoList.get(position).getNomerAvto());
                                context.startActivity(intent);
                                return true;
                            case R.id.detals_menu:
                                intent = new Intent((Activity) context,DetalsMarshActivity.class);
                                intent.putExtra("nomer", infoList.get(position).getNomerAvto());
                                intent.putExtra("detals", infoList.get(position).getDetals());
                                context.startActivity(intent);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(context.getApplicationContext(), "onDismiss", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

}