package com.example.mvvm_voyages_etu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;


import com.example.mvvm_voyages_etu.data.model.KitVoyage;
import com.example.mvvm_voyages_etu.data.model.OptionVoyage;
import com.example.mvvm_voyages_etu.data.repository.KitsVoyageRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class KitsVoyageViewModel extends ViewModel {

    private final KitsVoyageRepository repo;

    // L'UI observe ceci
    private final MutableLiveData<List<KitVoyage>> kits = new MutableLiveData<>(new ArrayList<>());

    @Inject
    public KitsVoyageViewModel(KitsVoyageRepository repo) {
        this.repo = repo;
        // Chargement initial
        kits.setValue(repo.getAll());
    }

    // --- Exposition ---
    public LiveData<List<KitVoyage>> getKitsVoyage() {
        return kits;
    }

    /** LiveData du kit demandé, dérivée de la liste observable */
    public LiveData<KitVoyage> getKitById(String id) {
        return Transformations.map(kits, list -> {
            if (list == null || id == null) return null;
            for (KitVoyage k : list) {
                if (id.equals(k.getId())) return k;
            }
            return null;
        });
    }

    // --- Actions ---
    /** Ajoute un kit puis notifie l'UI en réinjectant la liste à jour */
    public void add(String depart, String destination) {
        repo.add(new KitVoyage(depart, destination));
        kits.setValue(repo.getAll());
    }

    /** Ajoute une option à un kit, retourne true si succès, et notifie l'UI */
    public boolean addOptionToKitId(String kitId, String nom, double prix) {
        boolean ok = repo.addOptionToKitId(kitId, new OptionVoyage(nom, prix));
        if (ok) {
            kits.setValue(repo.getAll());
        }
        return ok;
    }
}
