package com.example.mvvm_voyages_etu.data.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class KitVoyage {

    @SerializedName("_id")
    private String id;
    private final String depart;
    private final String destination;
    @SerializedName("options")
    private final List<OptionVoyage> options = new ArrayList<>();

    /** Génère automatiquement un id unique */
    public KitVoyage(String depart, String destination) {
        this.id = UUID.randomUUID().toString();
        this.depart = depart;
        this.destination = destination;
    }


    public KitVoyage(String id, String depart, String destination) {
        this.id = id;
        this.depart = depart;
        this.destination = destination;
    }

    // ---- Getters ----
    public String getId() { return id; }
    public String getDepart() { return depart; }
    public String getDestination() { return destination; }

    public List<OptionVoyage> getLesOptions() {
        return options;
    }

    // ---- Métier ----
    /** Retourne le prix total des options */
    public double prix() {
        double total = 0;
        for (OptionVoyage o : options) {
            total += o.getPrix();
        }
        return total;
    }

    public void ajouterOption(OptionVoyage option) {
        if (option != null) options.add(option);
    }

    @Override
    public String toString() {
        return depart + " \u2192 " + destination;
    }

    public String getNbOptions() {
        return options.size() + " options";
    }
}

