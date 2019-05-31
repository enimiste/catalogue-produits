/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nouni.tuto.catalogueproduits.web;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import nouni.tuto.catalogueproduits.metier.Produit;

/**
 *
 * @author HP
 */
public class CatalogueModel {
    private List<Produit> items = new ArrayList<>();
    private boolean hasErrors;
    private String error = "";
    private Produit item = new Produit();
    private String keyword = "";
    private boolean onEditMode;

    public CatalogueModel() {
    }

    public List<Produit> getItems() {
        return items;
    }

    public void setItems(List<Produit> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
        this.hasErrors = error != null && error.length() > 0;
    }

    public Produit getItem() {
        return item;
    }

    public void setItem(Produit item) {
        this.item.setReference(item.getReference());
        this.item.setDesignation(item.getDesignation());
        this.item.setPrix(item.getPrix());
        this.item.setQuantite(item.getQuantite());
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    void fillItemFrom(HttpServletRequest req) {
        this.item.setReference(req.getParameter("ref"));
        this.item.setDesignation(req.getParameter("designation"));
        this.item.setPrix(Double.parseDouble(req.getParameter("prix")));
        this.item.setQuantite(Integer.parseInt(req.getParameter("quantite")));
    }

    void setKwfrom(HttpServletRequest req) {
        this.keyword = req.getParameter("keyword");
    }

    public void setIsEditMode(boolean onEditMode) {
        this.onEditMode = onEditMode;
    }
    
    public boolean getIsOnEditMode() {
        return onEditMode;
    }
    
    
}
