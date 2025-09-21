package com.example.mvvm_voyages_etu.network.api;


import com.example.mvvm_voyages_etu.data.model.OptionVoyage;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OptionVoyageApi {
    @GET("options")
    Call<List<OptionVoyage>> getOptions();

    @GET("options/{id}")
    Call<OptionVoyage> getOptionById(@Path("id") String id);
}
