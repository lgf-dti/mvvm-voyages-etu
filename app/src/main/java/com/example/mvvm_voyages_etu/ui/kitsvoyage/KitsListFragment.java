package com.example.mvvm_voyages_etu.ui.kitsvoyage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvm_voyages_etu.R;
import com.example.mvvm_voyages_etu.data.model.KitVoyage;
import com.example.mvvm_voyages_etu.viewmodel.KitsVoyageViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class KitsListFragment extends Fragment {

    private KitsVoyageViewModel vm;
    private KitsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kits_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        vm = new ViewModelProvider(requireActivity()).get(KitsVoyageViewModel.class);

        // RecyclerView + Adapter (clic -> navigation détail avec Safe Args)
        RecyclerView rv = v.findViewById(R.id.recyclerKits);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new KitsAdapter(kit -> {
            KitsListFragmentDirections.ActionKitsListToKitDetail action =
                    KitsListFragmentDirections.actionKitsListToKitDetail(kit.getId());
            NavHostFragment.findNavController(this).navigate(action);
        });
        rv.setAdapter(adapter);

        // Observe la liste des kits
        vm.getKitsVoyage().observe(getViewLifecycleOwner(), new Observer<List<KitVoyage>>() {
                    @Override
                    public void onChanged(List<KitVoyage> kitVoyages) {
                        adapter.submitList(new ArrayList<>(kitVoyages));
                    }
                });

        // Création d’un kit via le formulaire
        EditText etDepart = v.findViewById(R.id.etDepart);
        EditText etDestination = v.findViewById(R.id.etDestination);
        v.findViewById(R.id.btnAddKit).setOnClickListener(btn -> {
            String dep = etDepart.getText().toString().trim();
            String dest = etDestination.getText().toString().trim();
            if (dep.isEmpty()) { etDepart.setError("Requis"); return; }
            if (dest.isEmpty()) { etDestination.setError("Requis"); return; }
            vm.add(dep, dest);
            etDepart.setText(""); etDestination.setText("");
        });
    }
}
