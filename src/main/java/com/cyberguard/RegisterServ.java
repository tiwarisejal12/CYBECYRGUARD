package com.cyberguard;


import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.io.OutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RegisterServ extends HttpServlet {

   protected void doPost(HttpServletRequest request,
                      HttpServletResponse response)
        throws ServletException, IOException {

    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    try {

        Connection con = DBConnection.getConnection();

        String sql =
                "INSERT INTO users(name,email,password) VALUES(?,?,?)";

        PreparedStatement ps = con.prepareStatement(
                sql,
                Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            int userId = 0;

            ResultSet rs = ps.getGeneratedKeys();

    if (rs.next()) {

        userId = rs.getInt(1);

        HttpSession session = request.getSession();

        session.setAttribute("userId", userId);
        session.setAttribute("userName", name);
    }


            // Google Sheet URL
            String webAppUrl =
                    "https://script.google.com/macros/s/AKfycbw_ZSyuslzK9LlnibjwCtD5d619xtjwDrohsYhrUD2WWyh6558MfxuRAbeTtkwDE1dk/exec";

            String data =
                     "userId=" + URLEncoder.encode(String.valueOf(userId), "UTF-8")
                      + "&name=" + URLEncoder.encode(name, "UTF-8")
                      + "&email=" + URLEncoder.encode(email, "UTF-8")
                      + "&password=" + URLEncoder.encode(password, "UTF-8")
                       + "&type=Registration";
            URL url = new URL(webAppUrl);

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            conn.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded");

            OutputStream os = conn.getOutputStream();

            os.write(data.getBytes("UTF-8"));

            os.flush();
            os.close();
            System.out.println("Data saved in MySQL");
System.out.println("Sending data to Google Sheet...");
            int responseCode = conn.getResponseCode();

            System.out.println(
                    "Google Sheet Response = " + responseCode);

        
            response.sendRedirect("quiz.html");
        }

        con.close();

    } catch (Exception e) {

        e.printStackTrace();

        response.getWriter().println(
                "Registration Error: "
                + e.getMessage());
    }
   }
}