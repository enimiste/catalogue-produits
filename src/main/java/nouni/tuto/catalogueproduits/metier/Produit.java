/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nouni.tuto.catalogueproduits.metier;

/**
 *
 * @author HP
 */
public class Produit {

    private String reference;
    private String designation;
    private double prix;
    private int quantite;

    public Produit() {
    }

    public Produit(String reference, String designation, double prix, int quantite) {
        this.reference = reference;
        this.designation = designation;
        this.prix = prix;
        this.quantite = quantite;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Produit{" + "reference=" + reference + ", designation=" + designation + ", prix=" + prix + ", quantite=" + quantite + '}';
    }

}
