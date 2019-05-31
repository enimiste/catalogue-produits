<%-- 
    Document   : Catalogue
    Created on : 31 mai 2019, 00:01:31
    Author     : HP
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="nouni.tuto.catalogueproduits.web.CatalogueServlet"%>
<%@page import="nouni.tuto.catalogueproduits.web.CatalogueModel"%>
<%
    CatalogueModel model = CatalogueServlet.model(request);
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Catalogue de nos produits</title>
        <style>
            .search {
                border: 1px gray dotted;
                margin: 10px;
                padding: 10px;
            }
            .edit {
                border: 1px gray dotted;
                margin: 10px;
                padding: 10px;
            }
            .datatable {
                border: 1px gray dotted;
                margin: 10px;
                padding: 10px;
            }

            .datatable > table {
                border: 1px gray dotted;
            }
            .datatable > table > thead > tr > th {
                border: 1px gray dotted;
                background-color: pink;
                text-align: center;
            }
            .datatable > table > tbody > tr > td {
                border: 1px gray dotted;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <h1>Gestion du catalogue des produits</h1>
        <div class="search">
            <form action="${pageContext.request.contextPath}/catalogue.do" method="POST">
                <table >
                    <tbody>
                        <tr>
                            <td><input type="text" name="keyword" value="${model.keyword}" placeholder="Type a keyword to search for ..."/></td>
                            <td><input type="submit" name="action" value="Search"/>&nbsp;&nbsp;
                                <a href="${pageContext.request.contextPath}/catalogue.do">Cancel</a></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <div class="edit">
            <form action="${pageContext.request.contextPath}/catalogue.do" method="POST">
                <table >
                    <tbody>
                        <tr>
                            <td>Réference :</td>
                                <c:if test="${model.isOnEditMode}">
                                    <td><input type="text" value="${model.item.reference}" disabled=""/></td>
                                </c:if>
                                    <c:if test="${!model.isOnEditMode}">
                                    <td><input type="text" name="ref" value="${model.item.reference}"/></td>
                                </c:if>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Designation :</td>
                            <td><input type="text" name="designation" value="${model.item.designation}"/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Prix :</td>
                            <td><input type="number" step="any" name="prix" min="0" value="${model.item.prix}"/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Quantité :</td>
                            <td><input type="number" name="quantite" min="0" value="${model.item.quantite}"/></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="2"><input type="submit" name="action" value="Save"/></td>
                            <td><a href="${pageContext.request.contextPath}/catalogue.do">Cancel</a></td>
                        </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <c:if test="model.hasError()">      
            <div class="errors">
                <p>${model.error}</p>
            </div>
        </c:if>
        <div class="datatable">
            <table>
                <thead>
                    <tr>
                        <th>Réference</th>
                        <th>Désignation</th>
                        <th>Prix</th>
                        <th>Quantite</th>
                        <th colspan="2"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${model.getItems()}">
                        <tr>
                            <td>${item.getReference()}</td>
                            <td>${item.getDesignation()}</td>
                            <td>${item.getPrix()}</td>
                            <td>${item.getQuantite()}</td>
                            <td><a href="${pageContext.request.contextPath}/catalogue.do?ref=${item.reference}&action=Edit">Edit</a></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/catalogue.do?ref=${item.reference}" method="post">
                                    <input type="submit" name="action" value="Delete"/>
                                </form>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>

        </div>
    </body>
</html>
