package com.example.projectcyber.GameActivity.PauseMenu;

import android.media.audiofx.DynamicsProcessing;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectcyber.GameActivity.Equipment.Equipment;
import com.example.projectcyber.GameActivity.Equipment.Weapons.Weapon;
import com.example.projectcyber.Menu.StatUpgradeRecyclerAdapter;
import com.example.projectcyber.R;

import java.util.ArrayList;
import java.util.Collection;

public class EquipmentAdapter extends RecyclerView.Adapter<EquipmentAdapter.ViewHolder> {

    ArrayList<Equipment> list;

    public EquipmentAdapter(Collection<? extends Equipment> equipments){
        list = new ArrayList<>();
        list.addAll(equipments);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_in_pause, parent, false);
        return new EquipmentAdapter.ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.equipmentLevel.setText("Level : "+list.get(position).getLevel());
        holder.equipmentImage.setImageBitmap(list.get(position).getEquipmentBitmap());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView equipmentImage;
        TextView equipmentLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equipmentImage = itemView.findViewById(R.id.equipmentImage);
            equipmentLevel = itemView.findViewById(R.id.equipmentLevel);

        }
    }
}
