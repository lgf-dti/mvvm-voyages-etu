package com.example.mvvm_voyages_etu.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvm_voyages_etu.data.model.KitVoyage;
import com.example.mvvm_voyages_etu.data.model.OptionVoyage;
import com.example.mvvm_voyages_etu.network.api.KitVoyageApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class KitsVoyageRepository {

    private final KitVoyageApi kitVoyageApi;

    // Cache observable des kits (valeur par défaut : liste vide)
    private final MutableLiveData<List<KitVoyage>> kitsLive = new MutableLiveData<>(Collections.emptyList());

    @Inject
    public KitsVoyageRepository(KitVoyageApi kitVoyageApi) {
        this.kitVoyageApi = kitVoyageApi;
    }

    /** Expose le LiveData des kits. Déclenche un chargement initial si nécessaire. */
    public LiveData<List<KitVoyage>> getAll() {
        List<KitVoyage> current = kitsLive.getValue();
        if (current == null || current.isEmpty()) {
            refreshAll();
        }
        return kitsLive;
    }

    /** Force un rechargement depuis l'API. */
    public void refreshAll() {
        kitVoyageApi.getKits().enqueue(new Callback<List<KitVoyage>>() {
            @Override
            public void onResponse(Call<List<KitVoyage>> call, Response<List<KitVoyage>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    kitsLive.postValue(response.body());
                } else {
                    // à défaut, on publie une liste vide (tu peux conserver l’ancienne valeur si tu préfères)
                    kitsLive.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<List<KitVoyage>> call, Throwable t) {
                // erreur réseau → liste vide (ou laisse la valeur courante)
                kitsLive.postValue(new ArrayList<>());
            }
        });
    }


}
