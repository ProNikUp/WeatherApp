package com.example.eltgm.weatherapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherAdapter extends
        RecyclerView.Adapter<WeatherAdapter.ViewHolder>{


    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTmp;
        public TextView tvTime;
        public TextView tvDescr;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvTmp = (TextView) itemView.findViewById(R.id.tvTmp);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvDescr = (TextView) itemView.findViewById(R.id.tvDescr);
        }
    }//класс,хранящий отображения из item , вместо того,чтоб находить их каждый раз

    private WeatherDay[] mWeather;
    private Context mContext;

    public WeatherAdapter(Context context, WeatherDay[] weathers) {
        mWeather = weathers;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }//создаем объект viewholder и заполняем его отображениями из item

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // Get the data model based on position
        WeatherDay weather = mWeather[position];
        // Set item views based on your views and data model
        TextView tvDate = viewHolder.tvTime;
        tvDate.setText((weather.getDay())[0].getDate());
        TextView tvDescr = viewHolder.tvDescr;
        tvDescr.setText((weather.getDay())[3].getDescription());
        TextView tvTmp = viewHolder.tvTmp;
        tvTmp.setText("n: " + String.valueOf((weather.getDay())[1].getTemp()) + "\n" + "d: " + String.valueOf((weather.getDay())[6].getTemp()));

        tvTmp.setOnClickListener(new ItemClickListener(mContext,weather));
        tvDate.setOnClickListener(new ItemClickListener(mContext,weather));
        tvDescr.setOnClickListener(new ItemClickListener(mContext,weather));
    }//заполняем данными отображения, которые сохранили в viewholder

    @Override
    public int getItemCount() {
        return mWeather.length;
    }//номер элемента
}//адаптер,переводящий данные для recycler view