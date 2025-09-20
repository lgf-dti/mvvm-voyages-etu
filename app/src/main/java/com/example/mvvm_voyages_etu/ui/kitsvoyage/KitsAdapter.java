package com.example.mvvm_voyages_etu.ui.kitsvoyage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_voyages_etu.R;
import com.example.mvvm_voyages_etu.data.model.KitVoyage;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class KitsAdapter extends RecyclerView.Adapter<KitsAdapter.VH> {

    private final List<KitVoyage> data = new ArrayList<>();
    private final Consumer<KitVoyage> onClick;

    public KitsAdapter(Consumer<KitVoyage> onClick) {
        this.onClick = onClick;
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvDepartDestination, tvNbOptions;

        VH(@NonNull View itemView) {
            super(itemView);
            tvDepartDestination = itemView.findViewById(R.id.tvDepartDestination);
            tvNbOptions = itemView.findViewById(R.id.tvNbOptions);
        }

        void bind(KitVoyage kit, Consumer<KitVoyage> onClick) {
            tvDepartDestination.setText(kit.getDepart() + " → " + kit.getDestination());
            tvNbOptions.setText("Options : " + kit.getNbOptions());
            itemView.setOnClickListener(v -> onClick.accept(kit));
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kit, parent, false);
        return new VH(vItem);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(data.get(position), onClick);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void submitList(List<KitVoyage> list) {
        data.clear();
        if (list != null) data.addAll(list);
        notifyDataSetChanged();
    }
}
