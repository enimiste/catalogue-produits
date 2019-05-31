/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nouni.tuto.catalogueproduits.metier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class ProduitMetier implements IProduitMetier {

    @Override
    public List<Produit> listAll() {
        List<Produit> res = new ArrayList<>();
        try ( PreparedStatement ps = DBConnectionFactory.getInstance().prepareStatement("SELECT * FROM PRODUITS")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.add(produitFromResultSet(rs));
            }
        } catch (Exception ex) {
            Logger.getLogger(ProduitMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public List<Produit> filterByKeyword(String keyword) {
        if (keyword == null || keyword.length() == 0) {
            return listAll();
        }

        List<Produit> res = new ArrayList<>();
        try ( PreparedStatement ps = DBConnectionFactory.getInstance().prepareStatement("SELECT * FROM PRODUITS WHERE DESIGNATION LIKE ?")) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                res.add(produitFromResultSet(rs));
            }
        } catch (Exception ex) {
            Logger.getLogger(ProduitMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    @Override
    public Optional<Produit> findByRef(String ref) {
        try ( PreparedStatement ps = DBConnectionFactory.getInstance().prepareStatement("SELECT * FROM PRODUITS WHERE REF=?")) {
            ps.setString(1, ref);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return Optional.of(produitFromResultSet(rs));
            }
        } catch (Exception ex) {
            Logger.getLogger(ProduitMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    @Override
    public void save(Produit produit) {
        Optional<Produit> found = findByRef(produit.getReference());
        String sql;
        ThrowsException<PreparedStatement> fn;
        if (found.isPresent()) {
            //Update
            sql = "UPDATE PRODUITS SET DESIGNATION=?, PRIX=?, QUANTITE=? WHERE REF=?";
            fn = (PreparedStatement ps) -> {
                ps.setString(1, produit.getDesignation());
                ps.setDouble(2, produit.getPrix());
                ps.setInt(3, produit.getQuantite());
                ps.setString(4, produit.getReference());
            };
        } else {
            //Insert
            sql = "INSERT INTO PRODUITS(REF,DESIGNATION,PRIX,QUANTITE) VALUES(?,?,?,?)";
            fn = (PreparedStatement ps) -> {
                ps.setString(1, produit.getReference());
                ps.setString(2, produit.getDesignation());
                ps.setDouble(3, produit.getPrix());
                ps.setInt(4, produit.getQuantite());
            };
        }

        try ( PreparedStatement ps = DBConnectionFactory.getInstance().prepareStatement(sql)) {
            fn.apply(ps);
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProduitMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String ref) {
        try ( PreparedStatement ps = DBConnectionFactory.getInstance().prepareStatement("DELETE FROM PRODUITS WHERE REF=?")) {
            ps.setString(1, ref);
            ps.executeUpdate();
        } catch (Exception ex) {
            Logger.getLogger(ProduitMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Produit produitFromResultSet(ResultSet rs) throws SQLException {
        return new Produit(rs.getString("REF"), rs.getString("DESIGNATION"), rs.getDouble("PRIX"), rs.getInt("QUANTITE"));
    }

}

@FunctionalInterface
interface ThrowsException<T> {

    public void apply(T t) throws Exception;
}
