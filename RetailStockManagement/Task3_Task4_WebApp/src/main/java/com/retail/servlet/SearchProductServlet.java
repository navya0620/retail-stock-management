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
import java.util.List;

@WebServlet("/SearchProductServlet")
public class SearchProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        ProductDAO dao = new ProductDAO();
        List<Product> results = dao.searchProduct(keyword);

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
        if (results.isEmpty()) {
            out.println("<h3>No products found for: " + keyword + "</h3>");
        } else {
            out.println("<h3>Search Results</h3>");
            out.println("<table border='1' cellpadding='6'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Price</th><th>Quantity</th><th>Status</th></tr>");
            for (Product p : results) {
                out.println("<tr><td>" + p.getProductId() + "</td><td>" + p.getName()
                        + "</td><td>" + p.getPrice() + "</td><td>" + p.getQuantity()
                        + "</td><td>" + p.getStatus() + "</td></tr>");
            }
            out.println("</table>");
        }
        out.println("<br><a href='searchProduct.html'>Search Again</a>");
        out.println("</div></body></html>");
    }
}
