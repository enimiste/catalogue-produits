/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nouni.tuto.catalogueproduits.metier;

import java.util.function.UnaryOperator;

/**
 *
 * @author HP
 */
public class MainTest {

    public static void main(String[] args) {
        IProduitMetier metier = new ProduitMetier();
        Fn showAll = () -> {
            System.out.println("--------------------------------------");
            for (Produit p : metier.listAll()) {
                System.out.println(p);
            }

        };
        //List All
        showAll.apply();
        System.out.println("--------------------------------------");
        //Add new product
        metier.save(new Produit("TR001", "Produit de test SAP", 42521.25, 2));
        metier.save(new Produit("TR002", "Produit de test HP", 875.25, 20));
        metier.save(new Produit("TR003", "Produit de test", 100, 100));
        metier.save(new Produit("TR004", "Produit de test 2019", 10000.25, 1));
        showAll.apply();
        System.out.println("--------------------------------------");
        //Update an existing product
        metier.findByRef("TR003").ifPresent((p) -> {
            p.setDesignation("Photocopieuse Dell 400 series");
            p.setQuantite(700);
            metier.save(p);
            showAll.apply();
        });
        
        System.out.println("--------------------------------------");
        //Delete exiting product
        metier.delete("TR004");
        showAll.apply();
        System.out.println("--------------------------------------");
        //Filtre by keyword
        for (Produit item : metier.filterByKeyword("SAP")) {
            System.out.println(item);
        }
    }
}

@FunctionalInterface
interface Fn {

    public void apply();
}
