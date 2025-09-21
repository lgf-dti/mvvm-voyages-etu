package com.example.mvvm_voyages_etu.network.api;

import com.example.mvvm_voyages_etu.data.model.KitVoyage;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface KitVoyageApi {
    @GET("kits")
    Call<List<KitVoyage>> getKits();

    @GET("kits/{id}")
    Call<KitVoyage> getKitById(@Path("id") String id);
}
