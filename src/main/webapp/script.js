function checkPassword() {

    let password =
        document.getElementById("password").value;

    let score = 0;

    if (password.length >= 8) {
        score++;
    }

    if (/[A-Z]/.test(password)) {
        score++;
    }

    if (/[a-z]/.test(password)) {
        score++;
    }

    if (/[0-9]/.test(password)) {
        score++;
    }

    if (/[^A-Za-z0-9]/.test(password)) {
        score++;
    }

    let result =
        document.getElementById("passwordResult");

    if (password.length === 0) {

        result.innerHTML =
            "Please enter a password.";

    } else if (score <= 2) {

        result.innerHTML =
            "🔴 Weak - Add length, numbers and special characters.";

    } else if (score <= 4) {

        result.innerHTML =
            "🟡 Medium - Improve the password further.";

    } else {

        result.innerHTML =
            "🟢 Strong - Good password habits!";
    }
    }
        

        
  