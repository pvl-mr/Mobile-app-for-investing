package com.example.investingmobileapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.investingmobileapp.models.InstrumentModel;

import java.util.List;

public class InstrumentAdapter  extends RecyclerView.Adapter<InstrumentAdapter.ViewHolder>{

    interface OnInstrumentClickListener {
        void OnInstrumentClick(InstrumentModel state, int position);
    }

    private final OnInstrumentClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<InstrumentModel> instrumentModels;

    InstrumentAdapter(Context context, List<InstrumentModel> states, OnInstrumentClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.instrumentModels = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public InstrumentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InstrumentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        InstrumentModel _instrumentModel = instrumentModels.get(position);
        int icon;
        if (_instrumentModel.getType().equalsIgnoreCase("stock")) {
            icon = R.drawable.ic_graph;
        } else {
            icon = R.drawable.ic_analytics;
        }
        holder.flagView.setImageResource(icon);
        holder.nameView.setText("Название: " + _instrumentModel.getName() + "\nЦена:" + _instrumentModel.getPrice());
        holder.capitalView.setText("Описание: " + _instrumentModel.getDescription()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.OnInstrumentClick(_instrumentModel, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return instrumentModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView nameView, capitalView;
        ViewHolder(View view){
            super(view);
            flagView = view.findViewById(R.id.icon);
            nameView = view.findViewById(R.id.goal);
            capitalView = view.findViewById(R.id.years);
        }
    }
}