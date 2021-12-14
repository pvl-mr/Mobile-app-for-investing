package com.example.investingmobileapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.investingmobileapp.models.PortfolioModel;

import java.util.List;

public class PortfolioAdapter  extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder>{

    interface OnPortfolioClickListener {
        void OnPortfolioClick(PortfolioModel state, int position);
    }

    private final OnPortfolioClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<PortfolioModel> portfolioModels;

    PortfolioAdapter(Context context, List<PortfolioModel> states, OnPortfolioClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.portfolioModels = states;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public PortfolioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PortfolioAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PortfolioModel _portfolioModel = portfolioModels.get(position);

        String message = (_portfolioModel.getMessage() == null) ? "" : "\nАнализ: " + _portfolioModel.getMessage();
        holder.flagView.setImageResource(R.drawable.ic_portfolio);
        holder.nameView.setText("Цель: " + _portfolioModel.getGoal());
        holder.capitalView.setText("Срок: " + _portfolioModel.getYears() + message);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.OnPortfolioClick(_portfolioModel, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return portfolioModels.size();
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