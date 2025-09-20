package com.example.mvvm_voyages_etu.ui.kitsvoyage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mvvm_voyages_etu.R;
import com.example.mvvm_voyages_etu.data.model.OptionVoyage;

import java.util.ArrayList;
import java.util.List;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.VH> {
    private final List<OptionVoyage> data = new ArrayList<>();

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_option, parent, false);
        return new VH(vItem);
    }

    @Override public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(data.get(position));
    }

    @Override public int getItemCount() { return data.size(); }

    public void submitList(List<OptionVoyage> list) {
        data.clear();
        if (list != null) data.addAll(list);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvNom, tvPrix;
        VH(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvNom);
            tvPrix = itemView.findViewById(R.id.tvPrix);
        }
        void bind(OptionVoyage option) {
            tvNom.setText(option.getNom());
            tvPrix.setText(String.format("%.2f €", option.prix()));
        }
    }
}
