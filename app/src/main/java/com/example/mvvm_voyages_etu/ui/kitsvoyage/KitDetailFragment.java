package com.example.mvvm_voyages_etu.ui.kitsvoyage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mvvm_voyages_etu.R;
import com.example.mvvm_voyages_etu.viewmodel.KitsVoyageViewModel;
import java.util.ArrayList;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class KitDetailFragment extends Fragment {

    private KitsVoyageViewModel vm;
    private String kitId;
    private OptionsAdapter adapter;
    private TextView tvHeader, tvTotal;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kit_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        vm = new ViewModelProvider(requireActivity()).get(KitsVoyageViewModel.class);
        kitId = KitDetailFragmentArgs.fromBundle(getArguments()).getKitId();

        tvHeader = v.findViewById(R.id.tvHeader);
        tvTotal  = v.findViewById(R.id.tvTotal);

        RecyclerView rv = v.findViewById(R.id.recyclerOptions);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new OptionsAdapter();
        rv.setAdapter(adapter);

        // Observe le kit sélectionné
        vm.getKitById(kitId).observe(getViewLifecycleOwner(), kit -> {
            if (kit == null) {
                tvHeader.setText(getString(R.string.app_name));
                tvTotal.setText("Total : 0.00 €");
                adapter.submitList(new ArrayList<>());
                return;
            }
            tvHeader.setText(kit.getDepart() + " → " + kit.getDestination());
            tvTotal.setText(String.format("Total : %.2f €", kit.prix()));
            adapter.submitList(new ArrayList<>(kit.getLesOptions()));
        });

        // Bouton pour aller vers la création d’option
        v.findViewById(R.id.btnAddOption).setOnClickListener(btn -> {
            KitDetailFragmentDirections.ActionKitDetailToCreateOption action =
                    KitDetailFragmentDirections.actionKitDetailToCreateOption(kitId);
            NavHostFragment.findNavController(this).navigate(action);
        });
    }
}
