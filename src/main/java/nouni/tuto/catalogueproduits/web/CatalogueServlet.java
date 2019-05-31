/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nouni.tuto.catalogueproduits.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nouni.tuto.catalogueproduits.metier.IProduitMetier;
import nouni.tuto.catalogueproduits.metier.ProduitMetier;

/**
 *
 * @author HP
 */
@WebServlet(urlPatterns = {"/catalogue.do"})
public class CatalogueServlet extends HttpServlet {

    public final static String MODEL = "model";
    protected IProduitMetier metier;

    public static CatalogueModel model(HttpServletRequest req) {
        return (CatalogueModel) req.getAttribute(MODEL);
    }

    @Override
    public void init() throws ServletException {
        metier = new ProduitMetier();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CatalogueModel model = new CatalogueModel();
        model.setItems(metier.listAll());
        String action = req.getParameter("action");
        if ("Edit".equals(action)) {
            String ref = req.getParameter("ref");
            if(ref == null) {
                resp.sendRedirect(req.getContextPath() + "/catalogue.do");
                return;
            } else {
                metier.findByRef(ref).ifPresent(p-> {
                    model.setItem(p);
                    model.setIsEditMode(true);
                });
            }
        }
        req.setAttribute(MODEL, model);
        req.getRequestDispatcher("Catalogue.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CatalogueModel model = new CatalogueModel();
        try {
            String action = req.getParameter("action");
            if ("Save".equals(action)) {
                model.fillItemFrom(req);
                metier.save(model.getItem());
                resp.sendRedirect(req.getContextPath() + "/catalogue.do");
                return;
            }
            if ("Delete".equals(action)) {
                metier.delete(req.getParameter("ref"));
                resp.sendRedirect(req.getContextPath() + "/catalogue.do");
                return;
            } else if ("Search".equals(action)) {
                model.setKwfrom(req);
                model.setItems(metier.filterByKeyword(model.getKeyword()));
            } else {
                resp.sendRedirect(req.getContextPath() + "/catalogue.do");
                return;
            }
        } catch (Exception ex) {
            model.setError(ex.getMessage());
        }

        req.setAttribute(MODEL, model);
        req.getRequestDispatcher("Catalogue.jsp").forward(req, resp);
    }

}
