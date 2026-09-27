package com.cyberguard;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {

                       Connection con  = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE email=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                  HttpSession session = request.getSession();

                  session.setAttribute("userId", rs.getInt("id"));
                  session.setAttribute("userName", rs.getString("name"));

                  response.sendRedirect("password.html");

}

            if (rs.next()) {

                HttpSession session = request.getSession();

                session.setAttribute("userId", rs.getInt("id"));
                session.setAttribute("userName", rs.getString("name"));

                response.sendRedirect("quiz.html");

            } else {

                response.getWriter().println("Invalid Email or Password");

            }

            con.close();

        } catch (Exception e) 
        {

            e.printStackTrace();
            response.getWriter().println("Login Error: " + e.getMessage());

        }
    }
}