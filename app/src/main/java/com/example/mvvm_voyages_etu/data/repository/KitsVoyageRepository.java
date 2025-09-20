package com.example.mvvm_voyages_etu.data.repository;

import com.example.mvvm_voyages_etu.data.model.KitVoyage;
import com.example.mvvm_voyages_etu.data.model.OptionVoyage;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KitsVoyageRepository {

    private final List<KitVoyage> kits = new ArrayList<>();

    @Inject
    public KitsVoyageRepository() { }

    /** Retourne une copie pour éviter les modifications externes */
    public List<KitVoyage> getAll() {
        return new ArrayList<>(kits);
    }

    /** Ajoute un kit (au début de la liste) */
    public void add(KitVoyage kit) {
        kits.add(0, kit);
    }

    /** Ajoute une option au kit correspondant à l'id */
    public boolean addOptionToKitId(String kitId, OptionVoyage option) {
        for (KitVoyage k : kits) {
            if (k.getId().equals(kitId)) {
                k.ajouterOption(option);
                return true;
            }
        }
        return false;
    }
}

