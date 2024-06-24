window.onload = function() {
    // Create a new button element for login
    var loginButton = document.createElement("button");
    loginButton.innerHTML = "Go to Login Page";
    loginButton.className = "swagger-ui-btn";

    // Append the button to the Swagger UI header
    var header = document.querySelector(".swagger-ui .topbar");
    if (header) {
        header.appendChild(loginButton);
    }

    // Add click event to the button
    loginButton.onclick = function() {
        window.location.href = "/login.html";
    };
};
