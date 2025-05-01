package com.example.projectcyber.Menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcyber.R;

import java.util.ArrayList;

import com.example.projectcyber.UserLogic.User;

public class StatUpgradeRecyclerAdapter extends RecyclerView.Adapter<StatUpgradeRecyclerAdapter.ViewHolder> {

    ArrayList<StatItem> statItemList;
    User user;

    public StatUpgradeRecyclerAdapter(User user){
        this.user = user;
        this.statItemList = user.getStats();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.stat_upgrade_menu_item, parent, false);
        return new StatUpgradeRecyclerAdapter.ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StatItem item = statItemList.get(position);
        holder.statNameTextView.setText(item.getName());
        holder.statLvlTextView.setText("level : " + item.getLevel());
        int price = item.getPrice();
        if(price == -1){
            holder.priceTextView.setText("Max level!");
        }else{
            holder.priceTextView.setText("cost : " + item.getPrice());
        }

    }

    @Override
    public int getItemCount() {
        return statItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView statNameTextView;
        TextView statLvlTextView;
        TextView priceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            statNameTextView = itemView.findViewById(R.id.statNameTextView);
            statLvlTextView = itemView.findViewById(R.id.statLevelTextView);
            priceTextView = itemView.findViewById(R.id.statCostTextView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            if(statItemList.get(getAdapterPosition()).raiseLevel()){
                notifyDataSetChanged(); //all the data set changes once we buy something because the price...
            }
            //Log.d("stat", statItemList.get(getAdapterPosition()).getLevel() + "");

        }
    }
}
