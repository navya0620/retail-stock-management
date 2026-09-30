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

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("productId");
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int qty = Integer.parseInt(request.getParameter("quantity"));

        Product p = new Product(id, name, price, qty);

        ProductDAO dao = new ProductDAO();
        boolean success = dao.addProduct(p);

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
        if (success) {
            out.println("<h3>Product added successfully!</h3>");
            out.println("<p>ID: " + id + ", Name: " + name + ", Price: " + price
                    + ", Quantity: " + qty + ", Status: " + p.getStatus() + "</p>");
        } else {
            out.println("<h3>Failed to add product. Check server logs / DB connection.</h3>");
        }
        out.println("<a href='addProduct.html'>Add Another</a>");
        out.println("</div></body></html>");
    }
}
