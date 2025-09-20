package com.example.mvvm_voyages_etu.data.model;

public class OptionVoyage {
    private final String nom;
    private final double prix;
    private final boolean tarifReduit;

    public static final double COEF_TARIF_REDUIT = 0.8;

    // ---- Constructeurs ----
    public OptionVoyage(String nom, double prix) {
        this(nom, prix, false);
    }

    public OptionVoyage(String nom, double prix, boolean tarifReduit) {
        this.nom = nom;
        this.prix = prix;
        this.tarifReduit = tarifReduit;
    }

    // ---- Getters ----
    public String getNom() { return nom; }
    public double getPrix() { return prix(); }

    // ---- Métier ----
    /** Retourne le prix en tenant compte du tarif réduit */
    public double prix() {
        return tarifReduit ? prix * COEF_TARIF_REDUIT : prix;
    }

    @Override
    public String toString() {
        return nom + " -> " + prix() + " euros";
    }
}
