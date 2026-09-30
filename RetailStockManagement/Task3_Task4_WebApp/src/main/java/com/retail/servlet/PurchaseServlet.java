package com.retail.servlet;

import com.retail.dao.ProductDAO;
import com.retail.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String productId = request.getParameter("productId");
        int qty = Integer.parseInt(request.getParameter("quantity"));

        ProductDAO dao = new ProductDAO();
        Product p = dao.getProductById(productId);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><style>"
    + "body{font-family:Arial,sans-serif;background:#f4f6f8;padding:40px;}"
    + "div{background:#fff;max-width:400px;margin:auto;padding:25px;border-radius:10px;"
    + "box-shadow:0 2px 10px rgba(0,0,0,0.1);}"
    + "table{border-collapse:collapse;width:100%;}"
    + "th,td{padding:8px;border:1px solid #ddd;text-align:left;}"
    + "th{background:#2c3e50;color:#fff;}"
    + "a{color:#2c3e50;}"
    + "</style></head><body><div>");
        if (p == null) {
            out.println("<h3>Product not found: " + productId + "</h3>");
        } else if (qty > p.getQuantity()) {
            out.println("<h3>Purchase Rejected</h3>");
            out.println("<p>Requested quantity (" + qty + ") exceeds available stock ("
                    + p.getQuantity() + ").</p>");
        } else {
            double amount = dao.purchaseProduct(productId, qty);
            if (amount >= 0) {
                Product updated = dao.getProductById(productId);
                out.println("<h3>Purchase Successful - Bill</h3>");
                out.println("<p>Product: " + updated.getName() + "</p>");
                out.println("<p>Quantity Purchased: " + qty + "</p>");
                out.println("<p>Amount to Pay: " + amount + "</p>");
                out.println("<p>Remaining Stock: " + updated.getQuantity()
                        + " (" + updated.getStatus() + ")</p>");
            } else {
                out.println("<h3>Purchase failed. Try again.</h3>");
            }
        }
        out.println("<br><a href='purchase.html'>Make Another Purchase</a>");
        out.println("</div></body></html>");
    }
}
