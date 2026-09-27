<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>CyberGuard - Safety Score</title>

    <style>
        body{
            font-family:Arial,sans-serif;
            background:#f4f7fb;
            text-align:center;
            padding:50px;
        }

        .score-box{
            max-width:500px;
            margin:auto;
            background:white;
            padding:30px;
            border-radius:15px;
            box-shadow:0 5px 20px rgba(0,0,0,0.1);
        }

        .score{
            font-size:25px;
            margin:15px;
        }

        .overall{
            font-size:32px;
            font-weight:bold;
            color:#6d28d9;
        }
    </style>
</head>

<body>

<div class="score-box">

    <h1>My Cyber Safety Score</h1>

    <div class="score">
        Quiz Score:
        <strong>
            <%= request.getAttribute("quizScore") %>
        </strong>
    </div>

    <div class="score">
        Data Safety Score:
        <strong>
            <%= request.getAttribute("safetyScore") %>
        </strong>
    </div>

    <hr>

    <div class="overall">
        Overall Score:
        <%= request.getAttribute("overall") %>
    </div>

    <br>

    <a href="Home.html">Back to Home</a>

</div>

</body>
</html>