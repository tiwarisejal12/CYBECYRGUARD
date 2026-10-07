package com.cyberguard;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int quizScore = 0;

        for (int i = 1; i <= 5; i++) {

            String answer = request.getParameter("q" + i);

            if ("correct".equals(answer)) {
                quizScore++;
            }
        }

        int safetyScore = 0;

        if (request.getParameter("otp") != null)
            safetyScore += 20;

        if (request.getParameter("unique") != null)
            safetyScore += 20;

        if (request.getParameter("twofa") != null)
            safetyScore += 20;

        if (request.getParameter("privacy") != null)
            safetyScore += 20;

        if (request.getParameter("sharing") != null)
            safetyScore += 20;

        int overall = (quizScore * 20 + safetyScore) / 2;

        try {

            Connection con = DBConnection.getConnection();
             // Session Data
            HttpSession session = request.getSession();

            Integer userId =
                    (Integer) session.getAttribute("userId");

            String userName =
                    (String) session.getAttribute("userName");

           String sql =
                 "INSERT INTO quiz_results "
                 + "(user_id, user_name, quiz_score, safety_score, overall_score) "
                 + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, userId);
            ps.setString(2, userName);
            ps.setInt(3, quizScore);
            ps.setInt(4, safetyScore);
            ps.setInt(5, overall);

            ps.executeUpdate();


            // Google Sheet URL
            String webAppUrl =
                    "https://script.google.com/macros/s/AKfycbxtzb-1-Sli6_jRC19UjSuDhBMUg1Vr74lPBsZTz4nQvv3OMhZIfJJnQkdo1JaKBVOT/exec";

            String data =
                    "userId=" + URLEncoder.encode(String.valueOf(userId), "UTF-8")
                    + "&userName=" + URLEncoder.encode(userName, "UTF-8")
                    + "&score=" + URLEncoder.encode(String.valueOf(overall), "UTF-8")
                    + "&type=Quiz";

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

            System.out.println(
                    "Quiz Google Sheet Response = "
                    + conn.getResponseCode());

            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        request.setAttribute("quizScore", quizScore);
        request.setAttribute("safetyScore", safetyScore);
        request.setAttribute("overall", overall);

        request.getRequestDispatcher("score.jsp")
                .forward(request, response);
    }
}