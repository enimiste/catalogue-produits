/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nouni.tuto.catalogueproduits.metier;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author HP
 */
public interface IProduitMetier {
    public List<Produit> listAll();
    public List<Produit> filterByKeyword(String keyword);
    public Optional<Produit> findByRef(String ref);
    /**
     * Create a new record or update an existing one
     * @param produit 
     */
    public void save(Produit produit);
    public void delete(String ref);
}
