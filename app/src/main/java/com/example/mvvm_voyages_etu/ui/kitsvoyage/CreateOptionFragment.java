package com.example.mvvm_voyages_etu.ui.kitsvoyage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mvvm_voyages_etu.R;
import com.example.mvvm_voyages_etu.viewmodel.KitsVoyageViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateOptionFragment extends Fragment {

    private KitsVoyageViewModel vm;
    private String kitId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle b) {
        super.onViewCreated(v, b);
        vm = new ViewModelProvider(requireActivity()).get(KitsVoyageViewModel.class);
        kitId = CreateOptionFragmentArgs.fromBundle(getArguments()).getKitId();

        EditText etNom  = v.findViewById(R.id.etNom);
        EditText etPrix = v.findViewById(R.id.etPrix);

        v.findViewById(R.id.btnSave).setOnClickListener(btn -> {
            String nom = etNom.getText().toString().trim();
            String prixStr = etPrix.getText().toString().trim();

            if (nom.isEmpty()) { etNom.setError("Requis"); return; }

            double prix;
            try {
                prix = Double.parseDouble(prixStr);
            } catch (NumberFormatException e) {
                etPrix.setError("Nombre invalide");
                return;
            }

            boolean ok = vm.addOptionToKitId(kitId, nom, prix);
            if (ok) {
                Toast.makeText(requireContext(), "Option ajoutée", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).navigateUp(); // retour au détail
            } else {
                Toast.makeText(requireContext(), "Échec de l’ajout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
