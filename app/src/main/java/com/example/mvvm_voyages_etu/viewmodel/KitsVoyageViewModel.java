package com.example.mvvm_voyages_etu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mvvm_voyages_etu.data.model.KitVoyage;
import com.example.mvvm_voyages_etu.data.repository.KitsVoyageRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class KitsVoyageViewModel extends ViewModel {

    private final KitsVoyageRepository repo;

    // On expose directement le LiveData du repository
    private final LiveData<List<KitVoyage>> kits;

    @Inject
    public KitsVoyageViewModel(KitsVoyageRepository repo) {
        this.repo = repo;
        this.kits = repo.getAll(); // pas de setValue ici
    }

    // --- Exposition ---
    public LiveData<List<KitVoyage>> getKitsVoyage() {
        return kits;
    }

    /** LiveData du kit demandé, dérivé de la liste observable */
    public LiveData<KitVoyage> getKitById(String id) {
        return Transformations.map(kits, list -> {
            if (list == null || id == null) return null;
            for (KitVoyage k : list) {
                if (id.equals(k.getId())) return k;
            }
            return null;
        });
    }

    /** Permettre à l’UI de forcer un rechargement */
    public void refresh() {
        repo.refreshAll();
    }

}
