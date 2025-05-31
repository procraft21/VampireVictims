package com.example.projectcyber.Menu;

import android.annotation.SuppressLint;
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


    private User user;
    private ArrayList<StatItem> statItemList;
    private MenuActivity activity;

    public StatUpgradeRecyclerAdapter(User user, MenuActivity activity){
        this.user = user;
        this.statItemList = user.getStats();
        this.activity = activity;
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
        if(price == StatItem.MAX_LEVEL_PRICE){
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

        /**
         * when clicking on a stat item, level up the stat if can.
         */
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onClick(View v){
            StatItem statItem =statItemList.get(getAdapterPosition());
            int price = statItem.getPrice();

            //check if the user has enough coins and the stat didn't reach max level.
            if(user.getCoins() > price && statItem.canLevelUp()){

                //remove the price from the amount the user has.
                user.setCoins(user.getCoins() - price);

                //raise the stat level.
                statItem.raiseLevel();

                //update the coins in the text view.
                activity.setCoinsText(user.getCoins());

                //all the data set changes once we buy something because of the price.
                notifyDataSetChanged();
            }

        }
    }
}
